package com.communitygroup.qa.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-20 18:18
 * @Modified by:
 **/
@RestControllerAdvice
public class BaseExceptionController {

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
