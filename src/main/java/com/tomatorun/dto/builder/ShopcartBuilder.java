package com.tomatorun.dto.builder;

import com.tomatorun.dto.Shopcart;

/**
 * @author:Gavin
 * @date 2015/2/25
 */
public class ShopcartBuilder implements DtoBuilder<Shopcart> {

    private Shopcart shopcart = new Shopcart();

    public ShopcartBuilder id(Long id){
        shopcart.setId(id);
        return this;
    }

    public ShopcartBuilder userGoodsId(Long userGoodsId){
        shopcart.setUserGoodsId(userGoodsId);
        return this;
    }

    public ShopcartBuilder number(Integer number){
        shopcart.setNumber(number);
        return this;
    }

    public ShopcartBuilder userId(Long userId){
        shopcart.setUserId(userId);
        return this;
    }


    @Override
    public Shopcart build() {
        return shopcart;
    }
}
