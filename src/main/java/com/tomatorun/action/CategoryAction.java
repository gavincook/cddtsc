package com.tomatorun.action;

import com.tomatorun.repository.CategoryRepository;
import com.tomatorun.service.CategoryService;
import com.tomatorun.service.GoodsService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryAction {

    @Resource
    private CategoryService categoryService;

    @Resource
    private GoodsService goodsService;

    @Get("")
    @MenuMapping(name = "商品类别", url = "/category",code = "dt_category",parentCode = "dt")
    public ModelAndView showPage(){
        return new ModelAndView("/pages/cddtsc/category");
    }

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        return WebResponse.build().setResult(categoryService.listForPage(CategoryRepository.class,"list",params));
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        return WebResponse.build().setResult(categoryService.get(Maps.mapIt("id", id)));
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        categoryService.update(params);
        return WebResponse.build();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        categoryService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        goodsService.deleteByCategory(Strings.join(ids, ","));
        return WebResponse.build();
    }

    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request,@RequestParam(value="parentId",required = false)Long parentId){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("parentId",parentId);
        categoryService.add(params);
        return WebResponse.build();
    }

}
