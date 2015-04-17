package com.tomatorun.action;

import com.tomatorun.repository.CommentRepository;
import com.tomatorun.service.CommentService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.WebUser;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.MD5;
import org.moon.utils.Maps;
import org.moon.utils.ParamUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lywb on 2015/4/9.
 */
@Controller
@RequestMapping("/comment")
public class CommentAction {

    @Resource
    private CommentService commentService;

    @Config("commentStatus.checked")
    private String commentChecked;


    @Config("commentStatus.default")
    private String commentDefault;
    /**
     * 列表
     * @return
     */
    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        return WebResponse.build().setResult(commentService.listForPage(CommentRepository.class, "list", params));
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        return WebResponse.build().setResult(commentService.get(Maps.mapIt("id", id)));
    }

    /**
     * 更新评论
     * @param request
     * @return
     */
    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("userId",user.getId());
        commentService.update(params);
        return WebResponse.build();
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long id){
        Map<String,Object> params = Maps.mapIt("id", id);
        commentService.delete(params);
        return WebResponse.build();
    }

    /**
     * 新增评论
     * @param request      title,content,domain,cover
     * @param user
     * @return
     */
    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("status",commentDefault);
        params.put("userId",user.getId());
        commentService.add(params);
        return WebResponse.build();
    }

    /**
     * 审核
     * @param id
     * @return
     */
    @Post("/check")
    @ResponseBody
    public WebResponse check(@RequestParam("id")Long id){
        commentService.check(Maps.mapIt("id", id, "status", commentChecked));
        return WebResponse.build();
    }

}