package com.tomatorun.action;

import com.tomatorun.dto.Shop;
import com.tomatorun.service.ShopService;
import org.moon.message.WebResponse;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.WebUser;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author:Gavin
 * @date 2015/3/28 0028
 */
@Controller
@RequestMapping("/shop")
public class ShopAction {

    @Resource
    private ShopService shopService;

    @Post("/update")
    @ResponseBody
    public WebResponse update(@RequestParam("name") String name, @WebUser User user) {
        Shop shop = shopService.getForUser(user.getId());
        if (Objects.nonNull(shop)) {
            shop.setName(name);
            shopService.update(shop);
        } else {
            shop = new Shop();
            shop.setUserId(user.getId());
            shop.setName(name);
            shopService.add(shop);
        }
        return WebResponse.success(shop);
    }
}
