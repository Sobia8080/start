package com.wsm.compose.compose_base.aspect;

import com.alibaba.fastjson.JSON;
import com.wsm.compose.compose_util.core.MyException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @name: ApiLogAspect
 * @Author: wangshimin
 * @Date: 2019/11/15  11:12
 * @Description:
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     * 切点表达式:   execution(...)
     */
    @Pointcut("@annotation(com.wsm.compose.compose_base.annotation.LogAnnotation)")
    public void logPointCut() {
    }

    /**
     * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            saveLog(point, time, JSON.toJSONString(result));
        } catch (Exception e) {
            throw new MyException(0, "日志记录失败");
        }
        return result;
    }

    /**
     * 保存日志
     *
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time, String result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formatDate = dateFormat.format(new Date());
        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Object o : args) {
                stringBuffer.append(o.toString());
            }
        } catch (Exception e) {
        }
        log.info("请求时间：{}，请求的类名为：{}，请求的方法为：{}，请求的参数为：{}，消耗时间为：{}，返回结果为：{}", formatDate, className, methodName, stringBuffer, time, result);
    }
}
