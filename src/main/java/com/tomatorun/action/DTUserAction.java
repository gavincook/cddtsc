package com.tomatorun.action;

import com.tomatorun.repository.DTUserRepository;
import com.tomatorun.service.AttachmentService;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @Config("userType.supplier")
    private int supplierUserType;

    @Config("attachment.user")
    private int attachmentType;

    @Resource
    private AttachmentService attachmentService;

    @Get("")
    @MenuMapping(name = "用户管理", url = "/user",code = "dt_user",parentCode = "dt")
    public ModelAndView showUserPage(){
        return new ModelAndView("pages/cddtsc/user");
    }

    @Get("/member")
    @MenuMapping(name = "会员管理", url = "/user/member",code = "dt_member",parentCode = "dt")
    public ModelAndView showMemberPage(){
        return new ModelAndView("pages/cddtsc/user","memberPage",true);
    }
    /**
     * 获取会员列表
     * @param request
     * @param user
     * @return
     */
    @Get("/member/list")
    @ResponseBody
    public WebResponse listMembers(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        int userType = user.getType();
        if(userType == managerUserType){//只能管理员查看
            params.put("type",memberUserType);
        }else{
            return WebResponse.build().setPermission(false);
        }

        return WebResponse.build().setResult(dTUserService.listForPage(DTUserRepository.class,"list",params));
    }

    /**
     * 获取审核列表，管理员获取小组长列表，小组长获取该小组的组员
     * @param request
     * @param user
     * @return
     */
    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        int userType = user.getType();
        if(userType == memberUserType || userType == associatorUserType){
            params.put("type",null);
        }else if(userType == groupLeaderUserType){//小组长查看社员
            params.put("type",associatorUserType);
        }else if(userType == managerUserType){//管理员查看小组长
            params.put("type",groupLeaderUserType);
        }
        return WebResponse.build().setResult(dTUserService.listForPage(DTUserRepository.class,"list",params));
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        return WebResponse.build().setResult(dTUserService.get(Maps.mapIt("id", id)));
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
        dTUserService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
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
     * 注册
     * @param request
     * @param userName 手机号
     * @param realName 真实姓名
     * @param sex 性别 可选值为字典代码sex对应
     * @param IDNumber 身份证号
     * @param avatar [可选]头像
     * @param contact [可选]第二联系方式
     * @param attachments [可选]附件,可用于身份照上传,可多个,数组方式传递,需要对路径进行UTF-8编码
     * @param password 密码
     * @param type 为userType的几种可选
     * @return
     */
    @Post("/register")
    @ResponseBody
    public WebResponse register(HttpServletRequest request,
                                @RequestParam("userName")String userName,
                                @RequestParam("realName")String realName,
                                @RequestParam("sex")Integer sex,
                                @RequestParam("IDNumber")String IDNumber,
                                @RequestParam(value = "avatar",required = false)String avatar,
                                @RequestParam(value = "contact",required = false)String contact,
                                @RequestParam(value = "attachments",required = false)String[] attachments,
                                @RequestParam("password")String password,
                                @RequestParam("type")Integer type) throws UnsupportedEncodingException {
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("password", MD5.getCryptographicPassword(password));//加密

        if(Objects.isNull(type) ||
            (type != memberUserType && type != supplierUserType&&
             type != groupLeaderUserType )){//只允许会员、小组长、供应商注册
            return WebResponse.build().setSuccess(false).setResult("用户类型非法");
        }else if(type == memberUserType){//会员默认激活
            params.put("active",true);
        }else{//其他类型默认不激活，即需要审核
            params.put("active",false);
        }

        dTUserService.register(params);

        //添加身份照
        Long id = (Long) params.get("id");
        if(Objects.nonNull(attachments) && attachments.length>0){
            for(String attachment:attachments){
                attachment = URLDecoder.decode(attachment, "UTF-8");//前台进行了路径编码(因为文件名可能含有逗号,会影响数组传值)
                attachmentService.add(Maps.mapIt("referenceId",id,"url",attachment,"type", attachmentType));
            }
        }
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

    /**
     * 激活用户
     * @param userId
     * @return
     */
    @Post("/active")
    @ResponseBody
    public WebResponse activeUser(@RequestParam("userId")Long userId){
        dTUserService.activeUser(userId);
        return WebResponse.build();
    }
}
