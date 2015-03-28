package com.tomatorun.service.impl;

import com.tomatorun.dto.Shop;
import com.tomatorun.repository.ShopRepository;
import com.tomatorun.service.ShopService;
import org.moon.base.service.AbstractService;
import org.moon.base.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author:Gavin
 * @date 2015/3/28 0028
 */
@Service
public class ShopServiceImpl extends AbstractService implements ShopService {

    @Resource
    private ShopRepository shopRepository;

    @Override
    public Shop add(Shop shop) {
        shopRepository.add(shop);
        return shop;
    }

    @Override
    public Shop update(Shop shop) {
        shopRepository.update(shop);
        return shop;
    }

    @Override
    public Shop delete(Shop shop) {
        shopRepository.delete(shop);
        return shop;
    }

    @Override
    public Shop getForUser(Long userId) {
        return shopRepository.checkIfExists(userId);
    }

}
