package com.nhn.sadari.minidooray.gateway.advice;

import com.nhn.sadari.minidooray.gateway.exception.GitEmailNotFountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class LoginAdvice {
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.initDirectFieldAccess();
//    }

    @ExceptionHandler(value = {GitEmailNotFountException.class})
    public ModelAndView gitEmailNotFoundException(HttpServletRequest req, Exception e) {
        log.error("login Error = {}", e);
        ModelAndView mav = new ModelAndView();

        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("login/login_form");

        return mav;
    }


}
