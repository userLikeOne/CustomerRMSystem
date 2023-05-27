package com.dxf.springboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * annotations:处理含有对应注解的类
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        if (e.getMessage().contains("Duplicate entry")){ // 判断错误信息是否包含Duplicate entry=二次录入
            String[] split = e.getMessage().split(" "); // 以空格隔开错误信息
            String errorName = split[2]; // 拿到添加错误的名称
            return R.error("错误：姓名"+errorName+"重复添加");
        }

        return R.error("未知错误");
    }
}
