package com.wsm.compose.compose_util.core;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @name: ValidatorUtil
 * @Author: wangshimin
 * @Date: 2019/11/18  15:01
 * @Description:
 */
public class ValidatorUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validateEntity(T obj) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        Map<String, String> errorMsg = new HashMap<>(set.size());
        if (set != null && set.size() != 0) {
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
        }
        if (errorMsg.size() > 0) {
            throw new MyException(0, errorMsg.toString());
        }
    }

    public static <T> void validateProperty(T obj, String propertyName) {
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        Map<String, String> errorMsg = new HashMap<>(set.size());
        if (set != null && set.size() != 0) {
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
        }
        if (errorMsg.size() > 0) {
            throw new MyException(0, errorMsg.toString());
        }
    }
}
