package com.yujing.medicine.config;

import cn.hutool.json.JSONUtil;
import com.yujing.medicine.jwt.JWTUtil;
import com.yujing.medicine.utils.Result;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/21 14:52
*/
@Component
@WebFilter(urlPatterns = "/*", filterName = "CorsFilter")
public class CorsFilter implements Filter {

    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_CHA = "/checkCode";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String curOrigin = httpServletRequest.getHeader("Origin");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", curOrigin == null ? "true" : curOrigin);
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // 检查是否是登录页面的请求
        if (LOGIN_URL.equals(httpServletRequest.getRequestURI())) {
            // 如果是登录页面的请求，则直接放行，不进行JWT验证
            chain.doFilter(request, response);
            return;
        }
        if (LOGIN_CHA.equals(httpServletRequest.getRequestURI())) {
            // 如果是登录页面的请求，则直接放行，不进行JWT验证
            chain.doFilter(request, response);
            return;
        }

        //取出token
        String token = httpServletRequest.getHeader("token");
        //如果令牌存在 就证明拿到了token值
        if (token == null || token.equals("")){
            // 设置未授权或没登录（就是没有token）
            renderJson(httpServletResponse, Result.error("未授权"));
        }else {
            //校验是否通过jwt
            try{
                JWTUtil.verifyJWT(token);
                chain.doFilter(request, response);
            }catch (Exception e){
                renderJson(httpServletResponse,Result.error("验证失败"+e.getMessage()));
            }
        }

    }

    /**
     * 返回JSON数据
     * @param response
     * @param json
     */
    private void renderJson(HttpServletResponse response, Object json){
        // 设置编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置响应内容
        response.setContentType("application/json");
        // response里面向请求值写入的值
        try (PrintWriter writer = response.getWriter()){
            // 向请求写入你要介绍的对象
            writer.print(JSONUtil.toJsonStr(json));
        } catch (IOException e) {
            // 捕获异常
            e.printStackTrace();
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
