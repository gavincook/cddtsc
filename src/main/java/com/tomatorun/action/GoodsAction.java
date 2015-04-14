package com.tomatorun.action;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.AttachmentService;
import com.tomatorun.service.GoodsService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.message.WebResponse;
import org.moon.pagination.Pager;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import scala.xml.PrettyPrinter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsAction {

    @Config("attachment.goods")
    private int goodsImageType;

    @Resource
    private GoodsService goodsService;

    @Resource
    private AttachmentService attachmentService;

    @Get("/list")
    @ResponseBody
    public WebResponse list(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        Pager pager = goodsService.listForPage(GoodsRepository.class,"list",params);
        return WebResponse.build().setResult(pager);
    }

    @Get("/listGoodsOnSell")
    @ResponseBody
    public WebResponse listGoodsOnSell(HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        Pager pager = goodsService.listForPage(GoodsRepository.class,"listGoodsOnSell",params);
        return WebResponse.build().setResult(pager);
    }

    /**
     * 获取类别下的所有商品
     * @param request
     * @return
     */
    @Get("/listGoodsForCategory")
    @ResponseBody
    public WebResponse listGoodsForCategory(HttpServletRequest request,@RequestParam("categoryId")Long categoryId){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        Pager pager = goodsService.listForPage(GoodsRepository.class,"listGoodsOnSell",params);
        return WebResponse.success(pager);
    }

    /**
     * 商品选择页面，列出带封面的商品列表.只列出没有被该用户添加的商品
     * @param request
     * @return
     */
    @Get("/listWithCover")
    @ResponseBody
    public WebResponse listWithCover(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("attachmentType",goodsImageType);
        params.put("withCover",true);
        params.put("userId",user.getId());
        Pager pager = goodsService.listForPage(GoodsRepository.class,"list",params);
        return WebResponse.build().setResult(pager);
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        Map<String,Object> good = goodsService.get(Maps.mapIt("id", id));
        good.put("images", attachmentService.list(Maps.mapIt("referenceId", id, "type", goodsImageType)));
        return WebResponse.build().setResult(good);
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request,@RequestParam(value = "deletedIds",required = false)Long[] deleteIds,
                              @RequestParam("goodsId")Long goodsId,@RequestParam("id")Long[] ids, @RequestParam("name")String name, @RequestParam("specification")String[] specifications,
                              @RequestParam("price")Double[] prices,
                              @RequestParam("level")Integer[] levels,
                              @RequestParam("unit")Integer[] units,
                              @RequestParam("description") String description,
                              @RequestParam(value="attachments", required=false)String[] attachments,@RequestParam(value="attachmentIds", required=false)Long[] attachmentIds) throws UnsupportedEncodingException {
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);

        Long oldId;
        if(ids.length>0){
            oldId = ids[0];
        }else{
            oldId = deleteIds[0];
        }
        Map<String,Object> oldGoods = goodsService.getGoodsById(oldId);

        for(int i = 0,l=levels.length;i<l;i++){
            if(Objects.nonNull(ids[i])) {
                Map<String, Object> goods = Maps.mapIt("id", ids[i], "name", name, "price", prices[i],
                    "level", levels[i], "unit", units[i], "specification", specifications[i], "description", description);
                goodsService.update(goods);
            }else{//有新增的商品规格
                Map<String,Object> goods = Maps.mapIt("name",name,"categoryId",oldGoods.get("categoryId"),"price",prices[i],
                    "level",levels[i],"unit",units[i],"specification",specifications[i],"description",description,"goodsId",oldGoods.get("goodsId"));
                goodsService.add(goods);
            }
        }

        if(Objects.nonNull(deleteIds) && deleteIds.length>0){
            delete(deleteIds);
        }
        if(attachments != null && attachments.length > 0){
            for(String attachment : attachments){
                attachment = URLDecoder.decode(attachment,"UTF-8");
                attachmentService.add(Maps.mapIt("url", attachment, "referenceId", goodsId, "type", goodsImageType));
            }
        }
        if(attachmentIds != null && attachmentIds.length > 0){
            attachmentService.delete(Maps.mapIt("ids", Strings.join(attachmentIds, ",")));
        }
        return WebResponse.build();
    }

    @Post("/delete")
    @ResponseBody
    public WebResponse delete(@RequestParam("ids")Long ids[]){
        goodsService.delete(Maps.mapIt("ids", Strings.join(ids, ",")));
        return WebResponse.build();
    }

    /**
     * 添加商品
     * @param request
     * @param name 商品名字
     * @param specifications 规格
     * @param prices 价格
     * @param levels 等级
     * @param units 计量单位
     * @param categoryId 所属类别
     * @param attachments 商品图片
     * @return
     */
    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request, @RequestParam("name")String name,
                           @RequestParam("specification")String[] specifications,
                           @RequestParam("price")Double[] prices,
                           @RequestParam("level")Integer[] levels,
                           @RequestParam("unit")Integer[] units,
                           @RequestParam("categoryId")Long categoryId,
                           @RequestParam("description") String description,
                           @RequestParam(value="attachments", required=false)String[] attachments) throws UnsupportedEncodingException {
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        Long goodsId = null;
        for(int i = 0,l=levels.length;i<l;i++){
            Map<String,Object> goods = Maps.mapIt("name",name,"categoryId",categoryId,"price",prices[i],
                "level",levels[i],"unit",units[i],"specification",specifications[i],"description",description);
            if(Objects.nonNull(goodsId)){
                goods.put("goodsId",goodsId);
            }
            goodsService.add(goods);
            if(Objects.isNull(goodsId)){
                goodsId = Long.parseLong(goods.get("id").toString());
                goodsService.updateGoodsId(goodsId,goodsId);
            }
        }
        if(attachments != null && attachments.length > 0){
            for(String attachment : attachments){
                attachment = URLDecoder.decode(attachment,"UTF-8");
                attachmentService.add(Maps.mapIt("url", attachment, "referenceId", goodsId, "type", goodsImageType));
            }
        }
        return WebResponse.build().setResult(params.get("id"));
    }

    /**
     * 显示商品选择界面（供商）
     * @return
     */
    @Get("/selectPage")
    @MenuMapping(name = "商品选择", url = "/goods/selectPage",code = "dt_goods",parentCode = "dt")
    public ModelAndView showGoodsPageForSupplier(){
        return new ModelAndView("pages/cddtsc/selectGoods");
    }

    @Post("/select")
    @ResponseBody
    public WebResponse selectGoods(@RequestParam("inventory")Integer inventory,@RequestParam("goodsId")Integer goodsId,
                                   @WebUser User user, HttpServletRequest request){
        Map<String,Object> params = ParamUtils.getParamMapFromRequest(request);
        params.put("userId",user.getId());
        goodsService.select(params);
        return WebResponse.success(params);
    }

    /**
     * 列出已经选择了的商品
     * @param request
     * @param user
     * @return
     */
    @Get("/listWithCoverForSupplier")
    @ResponseBody
    public WebResponse listWithCoverForSupplier(HttpServletRequest request,@WebUser User user){
        Map<String,Object> params = ParamUtils.getAllParamMapFromRequest(request);
        params.put("attachmentType",goodsImageType);
        params.put("userId",user.getId());
        Pager pager = goodsService.listForPage(GoodsRepository.class,"listForSupplier",params);
        return WebResponse.success(pager);
    }

    /**
     * 删除供商选择的商品
     * @param user
     * @param selectGoodsId
     * @return
     */
    @Post("/select/delete")
    @ResponseBody
    public WebResponse deleteSelectGoods(@WebUser User user,@RequestParam("selectGoodsId")Long selectGoodsId){
        goodsService.deleteSelectGoods(selectGoodsId);
        return WebResponse.success();
    }

    /**
     * 批量更新价格
     * @param goodsId
     * @param prices
     * @return
     */
    @Post("/updatePrice")
    @ResponseBody
    public WebResponse updatePrice(@RequestParam("goodsId")Long[] goodsId,@RequestParam("prices")Double[] prices){
        for(int i=0,l=goodsId.length;i<l;i++){
            goodsService.updatePrice(prices[i],goodsId[i]);
        }
        return WebResponse.success();
    }
}
