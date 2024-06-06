package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//中心化异常处理器
@RestControllerAdvice
public class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    //返回响应码
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookNotFoundHandler(BookNotFoundException ex){
        //响应体中包含的信息
        return ex.getMessage();
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String bookAlreadyExistsHandler(BookAlreadyExistsException ex){
        return ex.getMessage();
    }

    //处理校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex){
        var errors=new HashMap<String,String>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }

    //捕获其它RuntimeException异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String otherRuntimeException(RuntimeException ex){
        return ex.getMessage();
    }
}
