package com.tomatorun.action;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.repository.OrderRepository;
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
@RequestMapping("/order")
public class OrderAction {
    
    @Resource
    private OrderService orderService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private ShopcartService shopcartService;

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        Map<String,Object> order = orderService.get(Maps.mapIt("id", id));
        order.put("orderdetail", orderDetailService.list(Maps.mapIt("orderId", order.get("id"))));
        return WebResponse.build().setResult(order);
    }

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        Pager pager = orderService.listForPage(OrderRepository.class,"list",params);
        List<Object> orders = pager.getItems();
        for (Object order : orders){
            ((HashMap<String,Object>)order).put("orderdetail", orderDetailService.list(Maps.mapIt("orderId", ((HashMap<String, Object>) order).get("id"))));
        }
        return WebResponse.build().setResult(pager);
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request, @RequestParam("id")Long id, @RequestParam("flag")String flag){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("flag", flag);
        orderService.update(params);
        return WebResponse.build();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        orderService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        return WebResponse.build();
    }

    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request, @RequestParam("userId")Long userId, @RequestParam("addressId")Long addressId,
                           @RequestParam("totalPrice")Long totalPrice, @RequestParam(value="userGoodsIds", required=false)Long[] userGoodsIds){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("addressId", addressId);
        params.put("totalPrice", totalPrice);
        orderService.add(params);
        Long orderId = Long.parseLong(params.get("id").toString());
        if(userGoodsIds != null && userGoodsIds.length > 0){
            for(Long userGoodsId : userGoodsIds){
                Map<String,Object> item = shopcartService.get(Maps.mapIt("id", userGoodsId));
                item.remove("id");
                item.put("orderId", orderId);
                item.put("purchaseNumber", item.get("nubmer"));
                orderDetailService.add(item);
            }
        }
        return WebResponse.build().setResult(params.get("id"));
    }

    @Post("/paidOrder")
    @ResponseBody
    public WebResponse paidOrder(@RequestParam("id")Long id){
        orderService.paidOrder(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    @Post("/distributeOrder")
    @ResponseBody
    public WebResponse distributeOrder(@RequestParam("id")Long id){
        orderService.distributeOrder(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    @Post("/orderArrived")
    @ResponseBody
    public WebResponse orderArrived(@RequestParam("id")Long id){
        orderService.orderArrived(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    @Post("/confirmOrder")
    @ResponseBody
    public WebResponse confirmOrder(@RequestParam("id")Long id){
        orderService.confirmOrder(Maps.mapIt("id", id));
        return WebResponse.build();
    }

}
