package cn.edu.nju.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jiayiwu on 16/7/24.
 */
@Controller
public class test {
    @RequestMapping("/")
    public String index(){
        return "test";
    }
    @RequestMapping("test")
    public void test(){

    }
}
