package com.nhn.sadari.minidooray.gateway.advice;

import com.nhn.sadari.minidooray.gateway.exception.AlreadyExistsException;
import com.nhn.sadari.minidooray.gateway.exception.GitEmailNotFountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class AccountAdvice {

    @ExceptionHandler(value = {GitEmailNotFountException.class})
    public ModelAndView gitEmailNotFoundException(HttpServletRequest req, Exception e) {
        log.error("login Error = {}", e);
        ModelAndView mav = new ModelAndView();

        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("login/login_form");

        return mav;
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ModelAndView registerFail(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("login/login_form");

        return mav;
    }
}
