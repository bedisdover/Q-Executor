package cn.edu.nju.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class test {

    @RequestMapping("test1")
    public String test(){
        System.out.println("获取到2html");
        return "test.jsp";
    }
}
