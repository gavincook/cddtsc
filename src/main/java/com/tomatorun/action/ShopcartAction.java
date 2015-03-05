package com.tomatorun.action;

import com.tomatorun.repository.OrderRepository;
import com.tomatorun.repository.ShopcartRepository;
import com.tomatorun.service.GoodsService;
import com.tomatorun.service.OrderDetailService;
import com.tomatorun.service.OrderService;
import com.tomatorun.service.ShopcartService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.pagination.Pager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopcart")
public class ShopcartAction {

    @Resource
    private ShopcartService shopcartService;

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        Map<String,Object> order = shopcartService.get(Maps.mapIt("id", id));
        return WebResponse.build().setResult(order);
    }

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        Pager pager = shopcartService.listForPage(ShopcartRepository.class,"list",params);
        return WebResponse.build().setResult(pager);
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request, @RequestParam("id")Long id, @RequestParam("number")String number){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("number", number);
        shopcartService.update(params);
        return WebResponse.build();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        shopcartService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        return WebResponse.build();
    }

    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request, @RequestParam("userId")Long userId, @RequestParam("userGoodsId")Long userGoodsId,
                           @RequestParam("number")Integer number){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("userGoodsId", userGoodsId);
        params.put("number", number);
        shopcartService.add(params);
        return WebResponse.build().setResult(params.get("id"));
    }

}
