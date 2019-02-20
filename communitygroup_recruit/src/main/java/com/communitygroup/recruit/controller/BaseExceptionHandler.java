package com.communitygroup.recruit.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program:
 * @description: 异常处理
 * @author: Song
 * @create: Created in 2019-02-20 14:10
 * @Modified by:
 **/
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "执行错误");
    }
}
