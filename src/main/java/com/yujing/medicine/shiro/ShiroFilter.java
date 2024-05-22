package com.yujing.medicine.shiro;

import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "shiroFilter",
    value = "/*",
                initParams = @WebInitParam(name = "targetFilterLifecycle",value = "true")
)
public class ShiroFilter extends DelegatingFilterProxy {
}
