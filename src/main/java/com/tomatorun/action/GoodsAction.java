package com.tomatorun.action;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.GoodsService;
import org.moon.message.WebResponse;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.Maps;
import org.moon.utils.ParamUtils;
import org.moon.utils.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsAction {

    @Resource
    private GoodsService goodsService;

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        return WebResponse.build().setResult(goodsService.listForPage(GoodsRepository.class,"list",params));
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        return WebResponse.build().setResult(goodsService.get(Maps.mapIt("id", id)));
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        goodsService.update(params);
        return WebResponse.build();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        goodsService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        return WebResponse.build();
    }

    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        goodsService.add(params);
        return WebResponse.build();
    }

}
