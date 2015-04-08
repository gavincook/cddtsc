package com.tomatorun.action;

import com.tomatorun.repository.ArticleRepository;
import com.tomatorun.service.ArticleService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.dictionary.service.DictionaryService;
import org.moon.exception.ApplicationRunTimeException;
import org.moon.message.WebResponse;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.MenuMapping;
import org.moon.rbac.domain.annotation.WebUser;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.Maps;
import org.moon.utils.ParamUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lywb on 2015/4/6.
 */
@Controller
@RequestMapping("/article")
public class ArticleAction {

    @Resource
    private ArticleService articleService;

    @Resource
    private DictionaryService dictionaryService;

    //普通文章
    @Config("articleType.article")
    private int article;

    //新闻
    @Config("articleType.article")
    private int news;

    //招聘信息
    @Config("articleType.article")
    private int joinUs;

    //招聘信息
    @Config("articleStatus.default")
    private int statusDefault;

    @Config("dic.articleType")
    private String articleTypeDicKey;

    @Config("articleStatus.checked")
    private String articleChecked;

    @Get()
    @MenuMapping(url = "/article",name = "文章管理",code = "dt_article",parentCode = "dt")
    public ModelAndView showOrderPage(){
        return new ModelAndView("pages/cddtsc/article");
    }

    /**
     * 文章列表
     * @return
     */
    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("articleTypeDicId",dictionaryService.getDictionaryIdByCode(articleTypeDicKey));
        return WebResponse.build().setResult(articleService.listForPage(ArticleRepository.class, "list", params));
    }

    /**
     * 获取文章详细信息
     * @param id
     * @param user
     * @return
     */
    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id,@WebUser User user){
        return WebResponse.build().setResult(articleService.get(Maps.mapIt("id", id)));
    }

    /**
     * 更新文章
     * @param request
     * @param articleType
     * @return
     */
    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request,@RequestParam("articleType")Integer articleType){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("articleType",articleType);
        articleService.update(params);
        return WebResponse.build();
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long id,@WebUser User user){
        int type = user.getType();
        Map<String,Object> params = Maps.mapIt("id", id);
        articleService.delete(params);
        return WebResponse.build();
    }

    /**
     * 新增文章
     * @param request      title,content,domain,cover
     * @param articleType   文章类型
     * @param user
     * @return
     */
    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request,@RequestParam("articleType")Integer articleType,@WebUser User user){
        if(articleType!=article && articleType!=news && articleType!=joinUs ){
            throw new ApplicationRunTimeException("文章类型非法");
        }
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("articleType",articleType);
        params.put("userId",user.getId());
        params.put("status",statusDefault);
        articleService.add(params);
        return WebResponse.build();
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @Get("/{id:\\d+}.html")
    public  ModelAndView preview(@PathVariable("id")Long id,@WebUser User user){
        Map<String,Object> article = articleService.get(Maps.mapIt("id",id));
        return new ModelAndView("/pages/cddtsc/articleDetail","article",article);
    }

    /**
     * 审核
     * @param id
     * @return
     */
    @Post("/check")
    @ResponseBody
    public WebResponse check(@RequestParam("id")Long id){
        articleService.check(Maps.mapIt("id", id, "status", articleChecked));
        return WebResponse.build();
    }
}