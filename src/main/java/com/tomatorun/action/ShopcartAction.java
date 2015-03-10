package com.tomatorun.action;

import com.tomatorun.dto.Shopcart;
import com.tomatorun.dto.builder.ShopcartBuilder;
import com.tomatorun.repository.OrderRepository;
import com.tomatorun.repository.ShopcartRepository;
import com.tomatorun.service.GoodsService;
import com.tomatorun.service.OrderDetailService;
import com.tomatorun.service.OrderService;
import com.tomatorun.service.ShopcartService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.pagination.Pager;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.MenuMapping;
import org.moon.rbac.domain.annotation.WebUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopcart")
public class ShopcartAction {

    @Resource
    private ShopcartService shopcartService;

    @Get()
    @MenuMapping(url = "shop",name = "订单管理",code = "dt_shopcart",parentCode = "dt")
    public ModelAndView showOrderPage(){
        return new ModelAndView("pages/cddtsc/shopcart");
    }

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

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        shopcartService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        return WebResponse.build();
    }

    /**
     * 添加购物车
     * @param request
     * @param user
     * @param userGoodsId
     * @param number
     * @return
     */
    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request, @WebUser User user, @RequestParam("userGoodsId")Long userGoodsId,
                           @RequestParam("number")Integer number){
        Shopcart shopcart = new ShopcartBuilder().userId(user.getId()).userGoodsId(userGoodsId).number(number).build();
        shopcartService.add(shopcart);
        return WebResponse.build().setResult(shopcart.getId());
    }

}
