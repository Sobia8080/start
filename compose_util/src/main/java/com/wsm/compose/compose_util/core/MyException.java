package com.wsm.compose.compose_util.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wsm
 *
 *
 * 用于自定义业务异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

}
