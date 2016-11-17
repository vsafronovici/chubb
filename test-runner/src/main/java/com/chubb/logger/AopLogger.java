package com.chubb.logger;

import com.chubb.exception.ChubbException;
import com.chubb.jorney.Verb;
import com.chubb.jorney.actions.Action;
import com.chubb.logger.levels.High;
import com.chubb.logger.levels.Low;
import com.chubb.logger.levels.Normal;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by ichistruga on 11/3/2016.
 */

@Aspect
@Component
public class AopLogger {
    public static final Logger logger = LogManager.getLogger(AopLogger.class);

    //static Logger logger = Logger.getLogger(AopLogger.class.getName());


    @Pointcut("execution(* *(..))")
    public void pcAllMethods() {
    }

    @Pointcut("within(com.chubb..*)")
    public void pcMethodsFromComChubb() {
    }

    @Pointcut("execution(void execute(String, String))")
    public void pcExecuteMethod() {
    }

    @Pointcut("execution(void *(..))")
    public void pcVoidAllMethodsAll() {
    }

    //@Around("pcMethodsFromComChubb() && pcExecuteMethod()")
    public void aroundExecuteMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.toShortString();
        logger.log(Normal.NORMAL, String.format("--> Starting %s", methodName));
        if (joinPoint.getArgs().length > 0) {
            String args = "";
            for (Object arg : joinPoint.getArgs()) {
                args += String.format("(Type:%s Value:%s)|", arg.getClass(), arg);
            }
            logger.log(Normal.NORMAL, String.format("--- Arguments %s", args));
        } else {
            logger.log(Normal.NORMAL, String.format("--- No arguments"));
        }
        joinPoint.proceed();

        logger.log(Normal.NORMAL, String.format("<-- Finished %s", methodName));


        logger.debug("====aop:" + methodName);

    }


    //@Around("pcMethodsFromComChubb()&& !pcExecuteMethod() && pcVoidAllMethodsAll()")
    public void aroundNonExecuteMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.toShortString();
        logger.log(Low.LOW, String.format("--> Starting %s", methodName));

        if (joinPoint.getArgs().length > 0) {
            String args = "";
            for (Object arg : joinPoint.getArgs()) {
                args += String.format("(Type:%s Value:%s)|", arg.getClass(), arg);
            }
            logger.log(Low.LOW, String.format("--- Arguments %s", args));
        } else {
            logger.log(Low.LOW, String.format("--- No arguments"));
        }

        joinPoint.proceed();

        logger.log(Low.LOW, String.format("<-- Finished %s", methodName));

        logger.debug("====aop:" + methodName);
    }

    @Pointcut("execution(* com.chubb.jorney.actions.*.execute(..))")
    public void pcAllActions() {
    }

    @Around("pcAllActions()")
    public void aroundAllActions(ProceedingJoinPoint joinPoint) {
        Action action = (Action) joinPoint.getThis();
        Verb verb = action.getVerb();

        //System.out.println(String.format("========= verb = %s; class=%s;", verb.name(), action.getClass().getSimpleName()));

        long startTime = System.currentTimeMillis();
        long endTime;


        try {
            joinPoint.proceed();
            endTime = System.currentTimeMillis();
            logger.log(Normal.NORMAL, String.format("%s (executed in %d millis)%s",
                    verb.name(), endTime - startTime, getArgsFormatted(joinPoint)));
        } catch (Throwable throwable) {
            endTime = System.currentTimeMillis();
            logger.error(String.format("%s failed (executed in %d millis)%s Reason: %s",
                    verb.name(), endTime - startTime, getArgsFormatted(joinPoint), throwable.getMessage()));
            throw (ChubbException) throwable;
        }
    }

    private static String getArgsFormatted(ProceedingJoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        if (params.length > 1) {
            return String.format("(paramName=%s, paramValue=%s)", params[0], params[1]);
        }
        return "";
    }

}
