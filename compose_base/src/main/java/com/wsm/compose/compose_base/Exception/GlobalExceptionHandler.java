package com.wsm.compose.compose_base.Exception;

import com.wsm.compose.compose_util.core.MyException;
import com.wsm.compose.compose_util.core.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


/**
 * 异常处理拦截器
 *
 * @author wsm
 * @date 2019年11月13日
 * 区分系统异常和业务异常，做不同的处理
 */
@CrossOrigin
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException ex) {
        return exceptionFormat(ex, "运行时异常");
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerExceptionHandler(NullPointerException ex) {
        return exceptionFormat(ex, "空指针异常");
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public Result classCastExceptionHandler(ClassCastException ex) {
        return exceptionFormat(ex, "类型转换异常");
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    public Result iOExceptionHandler(IOException ex) {
        return exceptionFormat(ex, "IO异常");
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    public Result noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return exceptionFormat(ex, "未知方法异常");
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Result indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return exceptionFormat(ex, "数组越界异常");
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Result requestNotReadable(HttpMessageNotReadableException ex) {
        System.out.println("400..requestNotReadable");
        return exceptionFormat(ex, "400错误");
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    public Result requestTypeMismatch(TypeMismatchException ex) {
        return exceptionFormat(ex, "400..TypeMismatchException");
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Result requestMissingServletRequest(MissingServletRequestParameterException ex) {
        return exceptionFormat(ex, "400..MissingServletRequest");
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result request405(HttpRequestMethodNotSupportedException ex) {
        return exceptionFormat(ex, "405错误,请使用正确的接口请求方式");
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public Result request406(HttpMediaTypeNotAcceptableException ex) {
        return exceptionFormat(ex, "406错误");
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public Result server500(RuntimeException ex) {
        return exceptionFormat(ex, "500错误");
    }

    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
    public Result requestStackOverflow(StackOverflowError ex) {
        return exceptionFormat(ex, "栈溢出");
    }

    //无权限
    @ExceptionHandler({UnauthorizedException.class})
    public Result requestStackOverflow(UnauthorizedException ex) {
        return exceptionFormat(ex, "无权限");
    }

    //权限认证失败
    @ExceptionHandler({AuthorizationException.class})
    public Result requestStackOverflow(AuthorizationException ex) {
        return exceptionFormat(ex, "权限认证失败");
    }

    //其他错误
    @ExceptionHandler({Exception.class})
    public Result exception(Exception ex) {
        return exceptionFormat(ex, "其他错误");
    }

    //自定义异常捕获
    @ExceptionHandler({MyException.class})
    public Result myException(MyException ex) {
        return exceptionFormat(ex, ex.getMessage());
    }

    //异常的处理方式
    private <T extends Throwable> Result exceptionFormat(T ex, String message) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.error(ex.getMessage(), ex);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return Result.error(message);
    }

}
