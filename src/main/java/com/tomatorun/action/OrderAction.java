package com.tomatorun.action;

import com.tomatorun.dto.Shopcart;
import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.repository.OrderRepository;
import com.tomatorun.service.GoodsService;
import com.tomatorun.service.OrderDetailService;
import com.tomatorun.service.OrderService;
import com.tomatorun.service.ShopcartService;
import org.apache.http.HttpResponse;
import org.moon.core.spring.config.annotation.Config;
import org.moon.exception.ApplicationRunTimeException;
import org.moon.message.WebResponse;
import org.moon.pagination.Pager;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.LoginRequired;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@LoginRequired
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
            String orderBtnText,action = "",currentStatus;
            boolean serverOpt = true;//后台操作
            switch ((Integer)((HashMap<String,Object>)order).get("status")){
                case 0 : orderBtnText = "支付";action="pay";currentStatus="待支付";serverOpt = false;break;
                case 1 : orderBtnText = "配送";action="distributeOrder";currentStatus="已支付";break;
                case 2 : orderBtnText = "完成配送";action="orderArrived";currentStatus="配送中";break;
                case 3 : orderBtnText = "确认收货";action="confirmOrder";currentStatus="等待确认收货";serverOpt = false;break;
                case 4 : orderBtnText = "已完成";currentStatus="已完成";serverOpt = false;break;
                default:orderBtnText = "已取消";currentStatus="已取消";serverOpt = false;
            }
            ((HashMap<String, Object>) order).put("orderBtnText",orderBtnText);//按钮文字
            ((HashMap<String, Object>) order).put("action",action);//下一步动作
            ((HashMap<String, Object>) order).put("currentStatus",currentStatus);//当前状态
            ((HashMap<String, Object>) order).put("serverOpt",serverOpt);//是否该后台操作
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
        params.put("userId",user.getId());//店铺管理员id
        Pager pager = orderService.listForPage(OrderRepository.class,"listForShop",params);
        List<Object> orders = pager.getItems();
        for (Object order : orders){
            String orderBtnText = "",action = "",currentStatus = "";
            boolean frontOpt = true;//前台操作
            switch ((Integer)((HashMap<String,Object>)order).get("status")){
                case 0 : orderBtnText = "支付";action="pay";currentStatus="待支付";break;
                case 1 : currentStatus="等待配送";frontOpt=false;break;
                case 2 : currentStatus="配送中";frontOpt=false;break;
                case 3 : orderBtnText = "确认收货";action="confirmOrder";break;
                case 4 : currentStatus="已完成";frontOpt = false;break;
                default: currentStatus="已取消";frontOpt = false;
            }
            ((HashMap<String, Object>) order).put("orderBtnText",orderBtnText);//按钮文字
            ((HashMap<String, Object>) order).put("action",action);//下一步动作
            ((HashMap<String, Object>) order).put("currentStatus",currentStatus);//当前状态
            ((HashMap<String, Object>) order).put("frontOpt",frontOpt);//是否该前台操作
            ((HashMap<String, Object>) order).put("orderDetail", orderDetailService.list(Maps.mapIt("orderId", ((HashMap<String, Object>) order).get("id"))));
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
    public void add(HttpServletRequest request,HttpServletResponse response, @WebUser User user,@RequestParam("addressId")Long addressId,
                           @RequestParam(value="userGoodsIds", required=false)Long[] userGoodsIds) throws IOException {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId", user.getId());
        params.put("addressId", addressId);
        List<Shopcart> shopcarts = shopcartService.getShopCartGoodsForUser(user.getId());
        List<Long> orderIds = new ArrayList<>();
        Map<Long,List<Shopcart>> groupedShopcart = shopcarts.stream().collect(Collectors.groupingBy(s -> s.getShopId()));
        for(List<Shopcart> shopcartList:groupedShopcart.values()){
            params.put("totalPrice", shopcartList.stream().mapToDouble(s -> s.getNumber() * s.getGoods().getPrice()).sum());
            orderService.add(params);
            Long orderId = Long.parseLong(params.get("id").toString());
            orderIds.add(orderId);
            shopcartList.stream().forEach(shopcart -> {
                orderDetailService.add(Maps.mapIt("orderId", orderId,
                        "purchaseNumber", shopcart.getNumber(), "userGoodsId", shopcart.getUserGoodsId()));
                //shopcartService.delete(shopcart.getId());
            });
        }

        response.sendRedirect("/order/pay.html?orderId="+Strings.join(orderIds,"&orderId="));
    }

    @Get("/pay.html")
    public ModelAndView showPay(@RequestParam("orderId")Long[] ids,@WebUser User user,HttpServletRequest request){
        List<Map<String,Object>> orders = orderService.list(Maps.mapIt("ids", ids, "userId", user.getId(), "unpaid", unpaidOrderType));
        if(ids.length != orders.size()){
            throw new ApplicationRunTimeException("参数非法");
        }
        double totalPrice;
        orders.stream().forEach(order -> {
            order.put("orderDetail", orderDetailService.list(Maps.mapIt("orderId", ((HashMap<String, Object>) order).get("id"))));
        });

        totalPrice = orders.stream().mapToDouble(order->Double.valueOf(order.get("totalPrice")+"")).sum();
        request.getSession().setAttribute("payOrderIds", ids);
        return new ModelAndView("pages/cddtsc/pay","orders",orders).addObject("totalPrice",totalPrice);
    }

    /**
     * 支付订单
     * @return
     */
    @Post("/pay")
    @ResponseBody
    public WebResponse paidOrder(@WebUser User user,
                                 HttpServletRequest request,@RequestParam("password")String password){
        Long[] payOrderIds = (Long[]) request.getSession().getAttribute("payOrderIds");

        if(MD5.getCryptographicPassword(password).equals(user.getPassword())) {
            orderService.pay(payOrderIds,user.getId());
            return WebResponse.success(Maps.mapIt("currentStatus", "等待配送", "frontOpt", false));
        }else{
            return WebResponse.fail(Maps.mapIt("errMsg","支付密码不正确"));
        }
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
        return WebResponse.success(Maps.mapIt("orderBtnText", "送达", "serverOpt", true, "action", "orderArrived"));
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
        return WebResponse.success(Maps.mapIt("currentStatus", "等待确认收货", "serverOpt", false));
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
        return WebResponse.success(Maps.mapIt("currentStatus", "已完成", "frontOpt", false));
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @Post("/cancelOrder")
    @ResponseBody
    public WebResponse cancelOrder(@RequestParam("id")Long id){
        orderService.cancelOrder(id);
        return WebResponse.success(Maps.mapIt("currentStatus", "已取消", "serverOpt", false));
    }
}
