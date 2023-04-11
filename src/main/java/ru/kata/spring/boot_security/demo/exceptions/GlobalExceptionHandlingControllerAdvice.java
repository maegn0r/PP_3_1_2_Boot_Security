package ru.kata.spring.boot_security.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {
    protected Logger logger;

    public GlobalExceptionHandlingControllerAdvice() {
        logger = LoggerFactory.getLogger(getClass());
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception)
            throws Exception {

        if (AnnotationUtils.findAnnotation(exception.getClass(),
                ResponseStatus.class) != null)
            throw exception;

        logger.error("Request: " + req.getRequestURI() + " raised " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("message", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
