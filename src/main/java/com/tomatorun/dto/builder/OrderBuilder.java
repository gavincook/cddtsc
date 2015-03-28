package com.tomatorun.dto.builder;

import com.tomatorun.dto.Order;

import java.util.Date;

/**
 * @author:Gavin
 * @date 2015/3/28 0028
 */
public class OrderBuilder implements DtoBuilder<Order> {

    private Order order = new Order();

    public OrderBuilder id(Long id){
        order.setId(id);
        return this;
    }

    public OrderBuilder userId(Long userId){
        order.setUserId(userId);
        return this;
    }

    public OrderBuilder shopId(Long shopId){
        order.setShopId(shopId);
        return this;
    }

    public OrderBuilder addressId(Long addressId){
        order.setAddressId(addressId);
        return this;
    }

    public OrderBuilder status(Integer status){
        order.setStatus(status);
        return this;
    }

    public OrderBuilder flag(Integer flag){
        order.setFlag(flag);
        return this;
    }

    public OrderBuilder time(Date time){
        order.setTime(time);
        return this;
    }

    public OrderBuilder number(String number){
        order.setNumber(number);
        return this;
    }

    @Override
    public Order build() {
        return order;
    }
}
