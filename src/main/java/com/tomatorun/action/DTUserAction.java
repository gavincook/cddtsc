package com.tomatorun.action;

import com.tomatorun.repository.DTUserRepository;
import com.tomatorun.service.DTUserService;
import org.moon.message.WebResponse;
import org.moon.rbac.domain.annotation.MenuMapping;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.Maps;
import org.moon.utils.ParamUtils;
import org.moon.utils.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class DTUserAction {

    @Resource
    private DTUserService dTUserService;

    @Get("")
    @MenuMapping(name = "会员管理", url = "/user",code = "dt_user",parentCode = "dt")
    public ModelAndView showPage(){
        return new ModelAndView("pages/cddtsc/user");
    }

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request){
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

}
