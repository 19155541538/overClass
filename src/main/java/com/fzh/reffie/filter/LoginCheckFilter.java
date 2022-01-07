package com.fzh.reffie.filter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reffie.common.BaseContext;
import com.fzh.reffie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/6 21:31
 * @description 检查用户是否已经登录的过滤器
 */

//filterName拦截器的名字  urlPatterns拦截器拦截的路径( /* 表示拦截所有的请求)
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        A. 获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到的请求{}", requestURI);
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee.logout",
                "/backend/**",
                "/front/**"
        };
//        B. 判断本次请求, 是否需要登录, 才可以访问  调用自定义判断 前端请求路径和后端允许放行的代码是否一致
        boolean check = check(urls, requestURI);

//        C. 如果不需要，则直接放行
        if (check) {
            log.info("本次请求不需要处理{}", requestURI);
            filterChain.doFilter(request, response);
//            这个返回值不写的话 会...???
            return;
        }

//        D. 判断登录状态，如果已登录，则直接放行  放行之后结束方法 return
        if (request.getSession().getAttribute("employee") != null) {

            //获取用户id
            Long empID =(Long) request.getSession().getAttribute("employee");
            //调用BaseContext设置值
            BaseContext.setCurrentId(empID);

            log.info("当前用于ID为{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

//        E. 如果未登录, 则返回未登录结果
        log.info("用户未登录");
        //如果用户没有登录返回未登录结果,如果输出方法向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 自定义方法  用于判断(前端发送的路径是否和后端指定可以放行的代码是否一致, 如果一致就返回true表示可以放行)当前请求路径 是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        //遍历urls
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
