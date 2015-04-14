package org.moon.rbac.interceptor;

import com.codahale.metrics.Timer;
import com.reeham.component.ddd.model.ModelContainer;
import com.tomatorun.service.CategoryService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.moon.core.domain.DomainLoader;
import org.moon.core.session.SessionContext;
import org.moon.exception.ApplicationRunTimeException;
import org.moon.log.domain.Log;
import org.moon.maintenance.component.PerformanceMonitorDataHolder;
import org.moon.rbac.domain.Menu;
import org.moon.rbac.domain.Role;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.*;
import org.moon.rbac.service.MenuService;
import org.moon.rbac.service.UserService;
import org.moon.utils.Maps;
import org.moon.utils.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 拦截器，主要拦截系统级菜单和权限注解
 *
 * @author Gavin
 * @version 1.0
 * @date 2012-12-22
 */
@Component
public class RbacInterceptor implements MethodInterceptor {

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private ModelContainer modelContainer;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PerformanceMonitorDataHolder performanceMonitorDataHolder;

    @Resource
    private DomainLoader domainLoader;

    @Resource
    private CategoryService categoryService;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        boolean hasPermission = true, accessMenu = true;
        Long currentUserId = (Long) SessionContext.getSession().getAttribute(User.CURRENT_USER_ID);
        Role currentRole = null;
        User currentUser = null;
        HttpServletRequest currentServletRequest = SessionContext.getRequest();
        String from = currentServletRequest.getRequestURL()
                + (currentServletRequest.getQueryString() == null ? "" : ("?" + currentServletRequest.getQueryString()));
        Method method = methodInvocation.getMethod();
        if ((method.isAnnotationPresent(LoginRequired.class) || method.getDeclaringClass().isAnnotationPresent(LoginRequired.class))
                && currentUserId == null) {//需要登录的操作
            if(method.isAnnotationPresent(ResponseBody.class)){//如果不是返回页面，则直接抛出异常
                throw new ApplicationRunTimeException("Current operation need login.");
            }else{//访问页面，则跳转为登录页面
                SessionContext.getResponse().sendRedirect(SessionContext.getContextPath() + "/user/login?from=" + from);
            }
        }

        if (currentUserId != null) {
            currentUser = userService.get(currentUserId);
            currentRole = currentUser.getRole();
        }
        // 权限拦截
        if (method.isAnnotationPresent(PermissionMapping.class)) {
            if (currentRole == null) {
                hasPermission = false;
            } else {
                if (currentRole.hasPermission(methodInvocation.getMethod().getAnnotation(PermissionMapping.class).code())) {
                    logger.debug("Current role can access the permission,code:{}", method.getAnnotation(PermissionMapping.class).code());
                    hasPermission = true;
                } else {
                    logger.debug("Current role can't access the permission,code:{}", method.getAnnotation(PermissionMapping.class).code());
                    hasPermission = false;
                }
            }
        }
        // 菜单拦截
        if (method.isAnnotationPresent(MenuMapping.class)) {
            if (method.isAnnotationPresent(NoMenuIntercept.class) || method.getDeclaringClass().isAnnotationPresent(NoMenuIntercept.class)) {
                accessMenu = true;
            } else if (currentRole == null) {
                SessionContext.getResponse().sendRedirect(SessionContext.getContextPath() + "/user/login?from=" + from);
            } else {
                if (currentRole.hasMenu((method.getAnnotation(MenuMapping.class).code()))) {
                    logger.debug("Current role can access the menu:{}", method.getAnnotation(MenuMapping.class).code());
                    accessMenu = true;
                } else {
                    logger.debug("Current role can't access the menu:{}", method.getAnnotation(MenuMapping.class).code());
                    accessMenu = false;
                }
            }
        }

        //日志记录
        if (method.isAnnotationPresent(LogRecord.class)) {
            if (currentUserId != null) {// 只对登录的用户进行日志处理,主要处理操作日志
                LogRecord logRecord = method.getAnnotation(LogRecord.class);
                new Log(currentUser.getUserName(), currentUserId, logRecord.action()).save();
            }
        }

        //可以访问
        if (hasPermission && accessMenu) {
            Object o = null;
            Timer timer = performanceMonitorDataHolder.getTimer(getRequestURI());
            Timer.Context context = timer.time();
            try {
                o = methodInvocation.proceed();
            } catch (Exception e) {
                throw e;
            } finally {
                context.stop();
            }
            if((o instanceof ModelAndView || (!method.isAnnotationPresent(ResponseBody.class) && o instanceof String))){
                ModelAndView result;
                if (o instanceof String) {
                    result = new ModelAndView((String) o);
                } else {
                    result = (ModelAndView) o;
                }

                if(Objects.nonNull(currentRole)) {
                    List<Menu> menus = domainLoader.load(Role.class, currentRole.getId()).getTopMenus();
                    Map<String, Object> data = Maps.mapIt("menus", menus);
                    data.put("currentUser", currentUser);
                    List<Map> subMenus;
                    MenuMapping currentMenuMapping = method.getAnnotation(MenuMapping.class);
                    if (Objects.nonNull(currentMenuMapping)) {//如果配置了menuMapping的菜单,则可以通过menuMapping获取父菜单
                        for (Menu m : menus) {
                            if (Objects.nonNull(currentMenuMapping) && m.getCode().equals(currentMenuMapping.parentCode())) {
                                subMenus = menuService.getSubMenusForRole(m.getId(), currentRole.getId());
                                data.put("subMenus", subMenus);
                                data.put("expandMenuCode", m.getCode());
                                for (Map menu : subMenus) {
                                    if (currentMenuMapping.code().equals(menu.get("code"))) {
                                        data.put("currentMenu", menu);//当前菜单
                                        break;
                                    }
                                }
                            }
                        }
                    } else {//通过xml配置的页面（顶级菜单和静态页面）
                        String url = currentServletRequest.getRequestURI() + "?" + currentServletRequest.getQueryString();
                        Menu currentMenu = menuService.getSpecialMenuForRole(url, currentRole.getId());
                        if (Objects.nonNull(currentMenu)) {
                            data.put("currentMenu", currentMenu);
                            for (Menu m : menus) {
                                if (m.getId().equals(currentMenu.getParentId()) || m.getCode().equals(currentMenu.getParentCode())) {
                                    subMenus = menuService.getSubMenusForRole(m.getId(), currentRole.getId());
                                    data.put("subMenus", subMenus);
                                    data.put("expandMenuCode", m.getCode());
                                }
                            }
                        }
                    }
                    result.addAllObjects(data);
                }

                result.addObject("categories", categoryService.list(Maps.mapIt("offset", 0, "pageSize", 15)));
                o = result;
            }
            return o;
        }

       throw new ApplicationRunTimeException("Permission denied");
    }

    /**
     * 获取当前请求的URI
     *
     * @return
     */
    private String getRequestURI() {
        return SessionContext.getRequest().getRequestURI();
    }


}
