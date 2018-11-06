package com.xu.demo.ctrl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * AOP
 *
 * Created by shaohua on 2018/10/30.
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    /*
      execution函数用于匹配方法执行的连接点，
      语法：execution(方法修饰符(可选)  返回类型  方法名  参数  异常模式(可选))
      参数部分允许使用通配符：
      *  匹配任意字符，但只能匹配一个元素
      .. 匹配任意字符，可以匹配任意多个元素，表示类时，必须和*联合使用
      +  必须跟在类名后面，如Horseman+，表示类本身和继承或扩展指定类的所有类
    */
    @Pointcut("execution(public * com.xu.demo.ctrl..*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("【AOP-Before】URL : " + request.getRequestURL().toString());
        log.info("【AOP-Before】HTTP_METHOD : " + request.getMethod());
        log.info("【AOP-Before】IP : " + request.getRemoteAddr());
        log.info("【AOP-Before】CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("【AOP-Before】ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturing(Object object) {
        log.info("【AOP-After】RESPONSE：" + object);
    }
}
