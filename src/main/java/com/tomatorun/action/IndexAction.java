package com.tomatorun.action;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.AttachmentService;
import com.tomatorun.service.GoodsService;
import com.tomatorun.service.ShopService;
import com.tomatorun.sms.SMSService;
import org.moon.core.domain.DomainLoader;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.pagination.Pager;
import org.moon.rbac.domain.Menu;
import org.moon.rbac.domain.Role;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.LoginRequired;
import org.moon.rbac.domain.annotation.MenuMapping;
import org.moon.rbac.domain.annotation.NoMenuIntercept;
import org.moon.rbac.domain.annotation.WebUser;
import org.moon.rest.annotation.Get;
import org.moon.rest.annotation.Post;
import org.moon.utils.Maps;
import org.moon.utils.Objects;
import org.moon.utils.ParamUtils;
import org.moon.utils.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

@Controller("cddtscAction")
@NoMenuIntercept
public class IndexAction {

    @Config("attachment.goods")
    private int goodsImageType;

    @Resource
    private DomainLoader domainLoader;

    @Resource
    private GoodsService goodsService;

    @Resource
    private AttachmentService attachmentService;

    @Resource
    private ShopService shopService;
    /**
     * show the index.html page
     * @return
     * @throws Exception
     */
    @RequestMapping("/index.html")
    public ModelAndView index(@WebUser User user ){

        return new ModelAndView("pages/cddtsc/index");
    }

    @Get("/user/regist.html")
    public ModelAndView registPage(@WebUser User user){
        return new ModelAndView("pages/cddtsc/register");
    }

    @Get("/user/login.html")
    public ModelAndView loginPage(@WebUser User user){
        return new ModelAndView("pages/cddtsc/login");
    }

    /**
     * show the goods.html page
     * @return
     * @throws Exception
     */
    @RequestMapping("/{goodsId}_goods.html")
    public ModelAndView goods(@WebUser User user, HttpServletRequest request,@PathVariable("goodsId")Long goodsId){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("goodsId",goodsId);
        return new ModelAndView("pages/cddtsc/goods",
                "shops",goodsService.listForPage(GoodsRepository.class,"listShopForGoods",params))
                .addObject("goods",goodsService.getGoodsDetail(Maps.mapIt("id",goodsId)));
    }
    /**
     * show the goods.html page
     * @return
     * @throws Exception
     */
    @RequestMapping("/{userGoodsId}_item.html")
    public ModelAndView goodsDetail(@WebUser User user, HttpServletRequest request, @PathVariable("userGoodsId")Long userGoodsId){
        Map<String,Object> goodsDetail = goodsService.getGoodsForShop(userGoodsId);
        Map<String,Object> params = Maps.mapIt("referenceId",goodsDetail.get("id"),"type",goodsImageType);
        List<Map<String,Object>> goodsImages = attachmentService.list(params);
        System.out.println(goodsImages.size());
        return new ModelAndView("pages/cddtsc/goodsDetail").addObject("user",user).addObject("goods",goodsDetail).addObject("images",goodsImages);
    }

    /**
     * 获取要在主页展示的商品列表(默认一页30个) 弃用，使用goodsAction.listGoodsOnSell
     * @param request
     * @return
     */
    @Get("/listGoods")
    @ResponseBody
    @Deprecated
    public WebResponse listGoods(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("attachmentType",goodsImageType);
        params.put("offset",0);
        params.put("pageSize",15);
        Pager pager = goodsService.listForPage(GoodsRepository.class,"listGoods",params);
        return WebResponse.build().setResult(pager);
    }


    /**
     * 搜索
     * @param keyword
     * @return
     */
    @RequestMapping("/search.html")
    public ModelAndView search(@RequestParam(value = "keyword",required = false)String keyword,HttpServletRequest request){

        Map<String,Object> param = ParamUtils.getParamsMapForPager(request);
        param.put("keyword",keyword);
        return new ModelAndView("pages/cddtsc/search","goodsList",goodsService.listForPage(
                GoodsRepository.class,"listGoodsOnSell",param
        )).addObject("keyword",keyword);
    }

    @Get("/my_daotong.html")
    @LoginRequired
    public ModelAndView showMyPage(@WebUser User user){
        return new ModelAndView("pages/cddtsc/my_daotong","user",user.toAllMap()).addObject("shop",shopService.getForUser(user.getId()));
    }

    @Get("/category_{id}.html")
    public ModelAndView showCategoryPage(@WebUser User user,@PathVariable("id")String categoryId){
        return new ModelAndView("pages/cddtsc/categoryGoods","categoryId",categoryId);
    }
}
