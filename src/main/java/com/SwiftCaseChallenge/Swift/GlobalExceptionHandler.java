package com.SwiftCaseChallenge.Swift;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Throwable e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    // No @Override annotation for getErrorPath method
    public String getErrorPath() {
        return "/error";
    }
}
