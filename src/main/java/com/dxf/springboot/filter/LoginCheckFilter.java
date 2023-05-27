package com.dxf.springboot.filter;

import com.alibaba.fastjson.JSON;
import com.dxf.springboot.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截成功{}",request.getRequestURI());
        String[] uri={
                "/backend/**",
                "/front/**",
                "/employee/login",
                "/employee/logout",
        };
        // 1、获取本次请求的URI
        String requestURI = request.getRequestURI();
        // 2、判断本次请求是否需要处理
        boolean checkURI = checkURI(uri, requestURI);
        // 3、如果不需要处理，则直接放行
        if (checkURI){
            log.info("不需要处理，通过");
            filterChain.doFilter(request,response);
            return;
        }
        // 4、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee")!=null)
        {
            log.info("需要处理，用户id{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        // 5、如果未登录则返回未登录结果
        log.info("未登录，返回登录页面");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 判断本次请求是否需要处理
     * @param uri
     * @param requestURI
     * @return
     */
    public boolean checkURI(String[] uri,String requestURI){
        for (String s : uri) {
            boolean match = PATH_MATCHER.match(s, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
