package com.insurance.util;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常处理页面
 * Created by xujunshuai on 2017/8/21.
 */
@ControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("e", e);
        mv.addObject("uri", request.getRequestURI());
        return mv;
    }
}
