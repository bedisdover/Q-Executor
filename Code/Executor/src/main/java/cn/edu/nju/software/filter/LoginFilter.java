package cn.edu.nju.software.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by song on 16-7-28.
 *
 * 过滤所有页面请求，判断是否登录，
 *      若未登录，转到登录界面
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"*.html"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("LoginFilter.doFilter");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
