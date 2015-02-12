package com.tomatorun.action;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.AttachmentService;
import com.tomatorun.service.GoodsService;
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
//        List<Object> goods = pager.getItems();
//        for (Object good : goods){
//            ((HashMap<String,Object>)good).put("images", attachmentService.list(Maps.mapIt("referenceId", ((HashMap<String, Object>) good).get("id"),"type", goodsImageType)));
//            System.out.println(good);
//        }
        return WebResponse.build().setResult(pager);
    }

    @Get("/get")
    @ResponseBody
    public WebResponse get(@RequestParam("id")Long id){
        Map<String,Object> good = goodsService.get(Maps.mapIt("id", id));
        good.put("images", attachmentService.list(Maps.mapIt("referenceId", good.get("id"),"type", goodsImageType)));
        System.out.println(good);
        return WebResponse.build().setResult(good);
    }

    @Post("/update")
    @ResponseBody
    public WebResponse update(HttpServletRequest request, @RequestParam("id")Long id, @RequestParam("name")String name, @RequestParam("specification")String specification,
                              @RequestParam(value="attachments", required=false)String[] attachments,@RequestParam(value="attachmentIds", required=false)Long[] attachmentIds){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("name", name);
        params.put("specification", specification);
        goodsService.update(params);
        Long goodId = Long.parseLong(params.get("id").toString());
        if(attachments != null && attachments.length > 0){
            for(String attachment : attachments){
                attachmentService.add(Maps.mapIt("url", attachment, "referenceId", goodId, "type", goodsImageType));
            }
        }
        System.out.println(attachmentIds);
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

    @Post("/add")
    @ResponseBody
    public WebResponse add(HttpServletRequest request, @RequestParam("name")String name, @RequestParam("specification")String specification,
                           @RequestParam("categoryId")Long categoryId, @RequestParam(value="attachments", required=false)String[] attachments){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("specification", specification);
        params.put("categoryId", categoryId);
        goodsService.add(params);
        Long goodId = Long.parseLong(params.get("id").toString());
        if(attachments != null && attachments.length > 0){
            for(String attachment : attachments){
                attachmentService.add(Maps.mapIt("url", attachment, "referenceId", goodId, "type", goodsImageType));
            }
        }
        return WebResponse.build().setResult(params.get("id"));
    }

}
