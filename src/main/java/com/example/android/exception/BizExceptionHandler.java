package com.example.android.exception;

import com.example.android.utils.ResultVOUtil;
import com.example.android.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BizExceptionHandler {
    @ExceptionHandler
    public Result handler(BizException e){
        return ResultVOUtil.error(e.getMessage());
    }
}
