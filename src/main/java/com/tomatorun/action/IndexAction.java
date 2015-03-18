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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("cddtscAction")
public class IndexAction {

    @Resource
    private DomainLoader domainLoader;

    /**
     * show the index page
     * @return
     * @throws Exception
     */
    @RequestMapping("/index.html")
    public ModelAndView index( ){

        System.out.println("test www");
        return new ModelAndView("pages/cddtsc/index");
    }
}
