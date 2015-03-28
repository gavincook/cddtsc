package com.tomatorun.action;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.AttachmentService;
import com.tomatorun.service.GoodsService;
import org.moon.core.domain.DomainLoader;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.pagination.Pager;
import org.moon.rbac.domain.Menu;
import org.moon.rbac.domain.Role;
import org.moon.rbac.domain.User;
import org.moon.rbac.domain.annotation.MenuMapping;
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
public class IndexAction {

    @Config("attachment.goods")
    private int goodsImageType;

    @Resource
    private DomainLoader domainLoader;

    @Resource
    private GoodsService goodsService;

    @Resource
    private AttachmentService attachmentService;

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
    @RequestMapping("/goodsDetail.html")
    public ModelAndView goodsDetail(@WebUser User user, HttpServletRequest request, @RequestParam("goodsId")Long id){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        Map<String,Object> goodsDetail = goodsService.getGoodsDetail(params);
        params.remove("id");
        params.put("referenceId",id);
        params.put("type",goodsImageType);
        List<Map<String,Object>> goodsImages = attachmentService.list(params);
        System.out.println(goodsImages.size());
        return new ModelAndView("pages/cddtsc/goodsDetail").addObject("user",user).addObject("goods",goodsDetail).addObject("images",goodsImages);
    }

    /**
     * 获取要在主页展示的商品列表(默认一页30个)
     * @param request
     * @return
     */
    @Get("/listGoods")
    @ResponseBody
    public WebResponse listGoods(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("attachmentType",goodsImageType);
        params.put("offset",0);
        params.put("pageSize",15);
        Pager pager = goodsService.listForPage(GoodsRepository.class,"listGoods",params);
        return WebResponse.build().setResult(pager);
    }
}
