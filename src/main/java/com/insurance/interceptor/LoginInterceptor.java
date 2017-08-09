package com.insurance.interceptor;

import com.insurance.bean.Register;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义拦截器，在controller请求处理之前执行， 这里用来验证是否已经登录
 * Created by xujunshuai on 2017/8/7.
 */
public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("......................拦截器启动......................");
        HttpSession session = httpServletRequest.getSession();

        Register register = (Register) session.getAttribute("register");
        if(register == null){
            httpServletResponse.sendRedirect("");
            return false;
        }else{
            session.setAttribute("register", register);
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("....................................处理完毕.............................");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("...............................拦截器关闭......................................");
    }
}
