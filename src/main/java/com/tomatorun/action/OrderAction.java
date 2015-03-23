package com.tomatorun.action;

import com.tomatorun.dto.Shopcart;
import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.repository.OrderRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderAction {
    
    @Resource
    private OrderService orderService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private ShopcartService shopcartService;

    @Resource
    private GoodsService goodsService;

    @Config("orderType.unpaid")
    private int unpaidOrderType;

    @Config("orderType.undistribute")
    private int undistributeOrderType;

    @Config("orderType.distributing")
    private int distributingOrderType;

    @Config("orderType.arrived")
    private int arrivedOrderType;

    @Config("orderType.confirmed")
    private int confirmedOrderType;

    @Config("orderType.cancel")
    private int cancelOrderType;

    @Get()
    @MenuMapping(url = "order",name = "订单管理",code = "dt_order",parentCode = "dt")
    public ModelAndView showOrderPage(){
        return new ModelAndView("pages/cddtsc/order");
    }

    @Get("/list_bought_items.html")
    @MenuMapping(url = "order",name = "订单管理",code = "dt_order",parentCode = "dt")
    public ModelAndView boughtItems(){
        return new ModelAndView("pages/cddtsc/listBought");
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        Map<String,Object> order = orderService.get(Maps.mapIt("id", id));
        order.put("orderdetail", orderDetailService.list(Maps.mapIt("orderId", order.get("id"))));
        return WebResponse.build().setResult(order);
    }

    /**
     * 罗列店铺订单
     * @param request
     * @param user
     * @return
     */
    //@Get("/list")
    //@ResponseBody
    public WebResponse list(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        Pager pager = orderService.listForPage(OrderRepository.class,"list",params);
        List<Object> orders = pager.getItems();
        for (Object order : orders){
            ((HashMap<String,Object>)order).put("orderDetail", orderDetailService.list(Maps.mapIt("orderId", ((HashMap<String, Object>) order).get("id"))));
        }
        return WebResponse.build().setResult(pager);
    }

    /**
     * 罗列店铺订单
     * @param request
     * @param user
     * @return
     */
    @Get("/list")
    @ResponseBody
    public WebResponse listForShop(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("userId",user.getId());//店铺管理员id
        Pager pager = orderService.listForPage(OrderRepository.class,"listForShop",params);
        List<Object> orders = pager.getItems();
        for (Object order : orders){
            String orderBtnText,action = "";
            switch ((Integer)((HashMap<String,Object>)order).get("status")){
                case 0 : orderBtnText = "支付";action="paidOrder";break;
                case 1 : orderBtnText = "配送";action="distributeOrder";break;
                case 2 : orderBtnText = "完成配送";action="orderArrived";break;
                case 3 : orderBtnText = "确认收货";action="confirmOrder";break;
                case 4 : orderBtnText = "已完成";break;
                default:orderBtnText = "已取消";
            }
            ((HashMap<String, Object>) order).put("orderBtnText",orderBtnText);//按钮文字
            ((HashMap<String, Object>) order).put("action",action);//下一步动作
            ((HashMap<String, Object>) order).put("orderDetail", orderDetailService.list(Maps.mapIt("orderId", ((HashMap<String, Object>) order).get("id"))));
        }
        return WebResponse.build().setResult(pager);
    }


    /**
     * 罗列已购买订单
     * @param request
     * @param user
     * @return
     */
    @Get("/list_bought_items")
    @ResponseBody
    public WebResponse listBoughtItems(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("userId",user.getId());
        Pager pager = orderService.listForPage(OrderRepository.class,"list",params);
        List<Object> orders = pager.getItems();
        for (Object order : orders){
            ((HashMap<String,Object>)order).put("orderDetail", orderDetailService.list(Maps.mapIt("orderId", ((HashMap<String, Object>) order).get("id"))));
        }
        return WebResponse.success(pager);
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

    @Post("/batchDelete")
    @ResponseBody
    public WebResponse batchDelete(@RequestParam("ids")Long ids[]){
        orderService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        return WebResponse.success();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("id")Long id){
        orderService.delete(Maps.mapIt("ids",id));
        return WebResponse.success();
    }

    /**
     * 提交订单
     * @param request
     * @param user
     * @param addressId
     * @param userGoodsIds
     * @return
     */
    @Post("/submit")
    public ModelAndView add(HttpServletRequest request, @WebUser User user,@RequestParam("addressId")Long addressId,
                           @RequestParam(value="userGoodsIds", required=false)Long[] userGoodsIds){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId", user.getId());
        params.put("addressId", addressId);
        List<Shopcart> shopcarts = shopcartService.getShopCartGoodsForUser(user.getId());

        Map<Long,List<Shopcart>> groupedShopcart = shopcarts.stream().collect(Collectors.groupingBy(s -> s.getShopId()));
        for(List<Shopcart> shopcartList:groupedShopcart.values()){
            params.put("totalPrice", shopcartList.stream().mapToDouble(s -> s.getNumber() * s.getGoods().getPrice()).sum());
            orderService.add(params);
            Long orderId = Long.parseLong(params.get("id").toString());
            shopcartList.stream().forEach(shopcart->{
                orderDetailService.add(Maps.mapIt("orderId",orderId,
                    "purchaseNumber",shopcart.getNumber(),"userGoodsId",shopcart.getUserGoodsId()));
                shopcartService.delete(shopcart.getId());
            });
        }

        return new ModelAndView("pages/cddtsc/pay");
    }

    /**
     * 支付订单
     * @param id
     * @return
     */
    @Post("/paidOrder")
    @ResponseBody
    public WebResponse paidOrder(@RequestParam("id")Long id){
        orderService.paidOrder(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    /**
     * 配送订单
     * @param id
     * @return
     */
    @Post("/distributeOrder")
    @ResponseBody
    public WebResponse distributeOrder(@RequestParam("id")Long id){
        orderService.distributeOrder(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    /**
     * 送达订单
     * @param id
     * @return
     */
    @Post("/orderArrived")
    @ResponseBody
    public WebResponse orderArrived(@RequestParam("id")Long id){
        orderService.orderArrived(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    /**
     * 买家确认收到
     * @param id
     * @return
     */
    @Post("/confirmOrder")
    @ResponseBody
    public WebResponse confirmOrder(@RequestParam("id")Long id){
        orderService.confirmOrder(Maps.mapIt("id", id));
        return WebResponse.build();
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @Post("/confirmOrder")
    @ResponseBody
    public WebResponse cancelOrder(@RequestParam("id")Long id){
        orderService.cancelOrder(id);
        return WebResponse.build();
    }
}
