package com.meidusa.venus.registry.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by huawei on 12/18/15.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String indexAction(){
        return "index";
    }




}
