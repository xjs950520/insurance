package com.insurance.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 配置拦截器
 * Created by xujunshuai on 2017/8/7.
 */
@Configuration
public class MyInterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        InterceptorRegistration ir = interceptorRegistry.addInterceptor(new LoginInterceptor());
        //配置拦截路径
       // ir.addPathPatterns("/lt");
        //配置不拦截路径
        ir.excludePathPatterns("/loginController/checkLogin");
    }
}
