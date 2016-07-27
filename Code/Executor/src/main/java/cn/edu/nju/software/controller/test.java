package cn.edu.nju.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jiayiwu on 16/7/24.
 */
@Controller
public class test {

    @RequestMapping("test1")
    public String test(){
        System.out.println("获取到2html");
        return "test.jsp";
    }
}
