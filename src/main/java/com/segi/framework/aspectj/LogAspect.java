package com.segi.framework.aspectj;

import com.segi.framework.aspectj.lang.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 * 
 * @author tianshuo
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     *   配置织入点
     */
    @Pointcut("@annotation(com.segi.framework.aspectj.lang.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 前置
     * @param joinPoint
     */
    @Before("logPointCut()")
    public void logBefore(JoinPoint joinPoint){
        //获得类名
        String clazzName = joinPoint.getTarget().getClass().getSimpleName();
        //获得方法名
        String methodName = joinPoint.getSignature().getName();
        //获得参数列表
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.info("[{}.{}] 开始处理请求 .\n请求参数：[{}]",clazzName, methodName, getLog(args));
        }
    }


    /**
     * 服务结束时调用
     * @param joinPoint 切点
     * @param reponse 响应
     */
    @AfterReturning(value ="logPointCut()",returning = "reponse")
    public void logAfterRunning(JoinPoint joinPoint, Object reponse){
        //获得类名
        String clazzName = joinPoint.getTarget().getClass().getSimpleName();
        //获得方法名
        String methodName = joinPoint.getSignature().getName();
        log.info("[{}.{}] 请求结束。\n 响应信息: . {}.",clazzName,methodName,reponse == null?"":reponse.toString());
    }



    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼接
     * @param args
     * @return
     */
    private String getLog(Object[] args){
        StringBuffer logSb = new StringBuffer("[");
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            logSb.append(arg == null ? null : arg.toString());
            if(i != args.length-1){
                logSb.append(",");
            }
        }
        logSb.append("]");
        return logSb.toString();
    }
}
