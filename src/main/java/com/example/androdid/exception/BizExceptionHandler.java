package com.example.androdid.exception;

import com.example.androdid.utils.ResultVOUtil;
import com.example.androdid.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BizExceptionHandler {
    @ExceptionHandler
    public Result handler(BizException e){
        return ResultVOUtil.error(e.getMessage());
    }
}
