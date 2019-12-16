package com.wsm.compose.compose_util.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * json结果集
 * @author wsm
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private String returnState;

    private String code;

    private String msg;

    private T data;

    public static <T> Result<T> success() {
        return new Result<T>("1", "1", "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>("1", "1", "success", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<T>("1", "1", msg, data);
    }

    public static <T> Result<T> error() {
        return new Result<T>("0", "0", "error", null);
    }

    public static <T> Result<T> error(T data) {
        return new Result<T>("0", "0", "error", data);
    }

    public static <T> Result<T> error(String msg, T data) {
        return new Result<T>("0", "0", msg, data);
    }

    public static <T> Result<T> error(String code, String msg, T data) {
        return new Result<T>("0", code, msg, data);
    }

    public static <T> Result<T> exception(String code, String msg, T data) {
        return new Result<T>("-1", code, msg, data);
    }
}