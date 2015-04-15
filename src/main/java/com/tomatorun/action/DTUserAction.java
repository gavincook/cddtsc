package com.tomatorun.action;

import com.tomatorun.dto.Address;
import com.tomatorun.dto.builder.AddressBuilder;
import com.tomatorun.repository.DTUserRepository;
import com.tomatorun.service.AttachmentService;
import com.tomatorun.service.DTUserService;
import com.tomatorun.service.ShopService;
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

    @Config("userType.superManager")
    private int superManager;

    @Config("userType.supplier")
    private int supplierUserType;

    @Config("attachment.user")
    private int attachmentType;

    @Resource
    private AttachmentService attachmentService;

    @Resource
    private ShopService shopService;

    @Get("")
    @MenuMapping(name = "用户管理", url = "/user",code = "dt_user",parentCode = "dt")
    public ModelAndView showUserPage(){
        return new ModelAndView("pages/cddtsc/user","pageType","").addObject("title","用户列表");
    }

    @Get("/member")
    @MenuMapping(name = "会员管理", url = "/user/member",code = "dt_member",parentCode = "dt")
    public ModelAndView showMemberPage(){
        return new ModelAndView("pages/cddtsc/user","userType",memberUserType).addObject("title","会员列表");
    }

    @Get("/manager")
    @MenuMapping(name = "管理员管理", url = "/user/manager",code = "dt_manager",parentCode = "dt")
    public ModelAndView showManagerPage(){
        return new ModelAndView("pages/cddtsc/user","userType",managerUserType).addObject("title","管理员列表");
    }

    @Get("/groupLeader")
    @MenuMapping(name = "小组长管理", url = "/user/groupLeader",code = "dt_groupLeader",parentCode = "dt")
    public ModelAndView showGroupLeaderPage(){
        return new ModelAndView("pages/cddtsc/user","userType",groupLeaderUserType).addObject("title","小组长列表");
    }

    @Get("/associator")
    @MenuMapping(name = "社员管理", url = "/user/associator",code = "dt_associator",parentCode = "dt")
    public ModelAndView showAssociatorPage(){
        return new ModelAndView("pages/cddtsc/user","userType",associatorUserType).addObject("title", "社员列表");
    }

    @Get("/my")
    @MenuMapping(name = "个人信息", url = "/user/my" , code = "dt_my" , parentCode = "dt")
    public ModelAndView showMyInfoPage(@WebUser User user){
        return new ModelAndView("pages/cddtsc/my","user",user.toAllMap()).addObject("shop",shopService.getForUser(user.getId()));
    }
    /**
     * 获取审核列表，管理员获取小组长列表，小组长获取该小组的组员
     * @param request
     * @param user
     * @return
     */
    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request,@WebUser User user,@RequestParam("type")Integer queryUserType){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        int userType = user.getType();
        params.put("type",null);
        params.put("groupLeaderId",null);
        if(queryUserType == managerUserType){//查看管理员
            if(userType == superManager){
                params.put("type",queryUserType);
            }
        }else if(queryUserType == memberUserType || queryUserType == groupLeaderUserType){//超级管理员和管理员可查看会员和小组长
            if(userType == managerUserType || userType == superManager){
                params.put("type",queryUserType);
            }
        }else if(queryUserType == associatorUserType){//小组长查看社员
            if(userType == groupLeaderUserType){
                params.put("type",queryUserType);
                params.put("groupLeaderId",user.getId());
            }
        }else{
            params.put("type",null);
        }

        return WebResponse.build().setResult(dTUserService.listForPage(DTUserRepository.class, "list", params));
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

    /**
     * 添加用户,可用于超级管理员添加管理员;和小组长添加社员。根据当前操作用户类型来判断.
     * @param request
     * @param password
     * @return
     */
    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request,@RequestParam("password")String password,@WebUser User user){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("password",MD5.getCryptographicPassword(password));
        int type = user.getType();
        if(type == superManager){//超级管理员添加管理员
            params.put("type",managerUserType);
            params.put("groupLeaderId",null);
        }else if(type == groupLeaderUserType){//小组长添加社员
            params.put("type",associatorUserType);
            params.put("groupLeaderId",user.getId());
        }else{
            return WebResponse.fail();
        }

        dTUserService.add(params);
        return WebResponse.success();
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
        return WebResponse.build().setResult(dTUserService.isUserRegistered(userName));
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

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    @Post("/disable")
    @ResponseBody
    public WebResponse disableUser(@RequestParam("userId")Long userId){
        dTUserService.disableUser(userId);
        return WebResponse.build();
    }

    /**
     * 重置密码为123456
     * @param userId
     * @return
     */
    @Post("/password/reset")
    @ResponseBody
    public WebResponse resetPassword(@RequestParam("userId")Long userId){
        dTUserService.resetPassword(userId, MD5.getCryptographicPassword("123456"));
        return WebResponse.build();
    }


    /**
     * 用户头像更新
     * @param avatar
     * @param user
     * @return
     */
    @Post("/avatar/update")
    @ResponseBody
    public WebResponse updateAvatar(@RequestParam("avatar")String avatar,@WebUser User user){
        dTUserService.updateAvatar(user.getId(),avatar);
        user.setAvatar(avatar);
        return WebResponse.build();
    }

    /**
     * 获取收货地址列表
     * @param user
     * @return
     */
    @Get("/addresses")
    @ResponseBody
    public WebResponse getAddresses(@WebUser User user){
        return WebResponse.build().setResult(dTUserService.getAddresses(user.getId()));
    }

    @Post("/address/add")
    @ResponseBody
    public WebResponse addAddress(@WebUser User user,@RequestParam("address")String address,
                                  @RequestParam("phoneNumber")String phoneNumber,
                                  @RequestParam("consignee")String consignee,
                                  @RequestParam(value = "isDefault",defaultValue = "false",required = false)Boolean isDefault){
        AddressBuilder ab = new AddressBuilder();
        Address ar = ab.address(address).userId(user.getId()).consignee(consignee).isDefault(isDefault)
            .phoneNumber(phoneNumber).build();
        return WebResponse.success(dTUserService.addAddress(ar));
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @Post("/address/delete")
    @ResponseBody
    public WebResponse removeAddress(@RequestParam("id")Long id){
        dTUserService.deleteAddress(id);
        return WebResponse.success();
    }

    @Get("/address/get")
    @ResponseBody
    public WebResponse getAddress(@RequestParam("id")Long id){
        return WebResponse.success(dTUserService.getAddress(id));
    }

    @Post("/address/update")
    @ResponseBody
    public WebResponse updateAddress(@WebUser User user,@RequestParam("address")String address,
                                     @RequestParam("id")Long id,
                                     @RequestParam("phoneNumber")String phoneNumber,
                                     @RequestParam("consignee")String consignee,
                                     @RequestParam(value = "isDefault",defaultValue = "false",required = false)Boolean isDefault){
        AddressBuilder ab = new AddressBuilder();
        Address ar = ab.address(address).id(id).userId(user.getId()).consignee(consignee).isDefault(isDefault)
            .phoneNumber(phoneNumber).build();
        return WebResponse.success(dTUserService.updateAddress(ar));
    }

    /**
     * 设置默认收货地址
     * @param id
     * @param user
     * @return
     */
    @Post("/address/default/set")
    @ResponseBody
    public WebResponse setDefaultAddress(@RequestParam("id")Long id,@WebUser User user){
        AddressBuilder ab = new AddressBuilder();
        Address ar = ab.id(id).userId(user.getId()).build();
        dTUserService.setDefaultAddress(ar);
        return WebResponse.success();
    }



    /**
     * 登出操作
     *
     * @return
     */
    @Get("/signout")
    public ModelAndView loginOut(HttpServletRequest request) {
        request.getSession().setAttribute(User.CURRENT_USER_ID, null);
        return new ModelAndView("pages/cddtsc/login");
    }
}
