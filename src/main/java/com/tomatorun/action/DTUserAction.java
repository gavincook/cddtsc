package com.tomatorun.action;

import com.tomatorun.repository.DTUserRepository;
import com.tomatorun.service.DTUserService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.MenuMapping;
import org.moon.rbac.domain.annotation.WebUser;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class DTUserAction {

    @Resource
    private DTUserService dTUserService;

    @Config("userType.member")
    private int memberUserType;

    @Config("userType.associator")
    private int associatorUserType;

    @Config("userType.groupLeader")
    private int groupLeaderUserType;

    @Config("userType.manager")
    private int managerUserType;


    @Get("")
    @MenuMapping(name = "会员管理", url = "/user",code = "dt_user",parentCode = "dt")
    public ModelAndView showPage(){
        return new ModelAndView("pages/cddtsc/user");
    }

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        return WebResponse.build().setResult(dTUserService.listForPage(DTUserRepository.class,"list",params));
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        return WebResponse.build().setResult(dTUserService.get(Maps.mapIt("id",id)));
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        dTUserService.update(params);
        return WebResponse.build();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        dTUserService.delete(Maps.mapIt("ids",Strings.join(ids,",")));
        return WebResponse.build();
    }

    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        dTUserService.add(params);
        return WebResponse.build();
    }

    /**
     * 注册：userName,realName,sex,type,password,IDNumber,avatar,active
     * @param request
     * @return
     */
    @Post("/register")
    @ResponseBody
    public WebResponse register(HttpServletRequest request,@RequestParam("password")String password,
                                @RequestParam("type")Integer type){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("password", MD5.getCryptographicPassword(password));//加密

        if(Objects.isNull(type) ||
            (type != memberUserType && type != associatorUserType&&
             type == groupLeaderUserType && type == managerUserType)){
            return WebResponse.build().setSuccess(false).setResult("用户类型非法");
        }else if(type == memberUserType){//会员默认激活
            params.put("active",true);
        }else{//其他类型默认不激活，即需要审核
            params.put("active",false);
        }

        dTUserService.register(params);
        return WebResponse.build();
    }

    /**
     * 检查用户名是否已经注册
     * @param userName
     * @return
     */
    @Post("/userName/check")
    @ResponseBody
    public WebResponse checkUserName(@RequestParam("userName")String userName){
        return WebResponse.build().setSuccess(dTUserService.isUserRegistered(userName));
    }
}
