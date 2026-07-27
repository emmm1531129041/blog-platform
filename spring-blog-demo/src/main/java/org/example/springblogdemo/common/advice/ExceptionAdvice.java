package org.example.springblogdemo.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.springblogdemo.common.exception.BlogException;
import org.example.springblogdemo.pojo.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;

//统一处理异常
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    //普通异常,抓所有异常
    @ExceptionHandler(exception = Exception.class)
    public Result handlerException(Exception e){
        log.error("发生异常, e:", e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler
    public Result handlerBlogException(BlogException e){
        log.error("发生异常, e:{}", e.getErrMsg());
        return Result.fail(e.getErrMsg());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(exception = {MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
    public Result handler(Exception e){
        List<String> errors = new ArrayList<>();
//        e.getAllErrors().forEach(error ->
//                errors.add(error.getDefaultMessage()));
        log.error("发生异常,参数校验失败", errors);
        return Result.fail("参数校验失败");
    }

}
