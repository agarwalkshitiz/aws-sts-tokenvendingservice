package com.paytm.tokenvendingservice.auth;

import java.lang.reflect.Method;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import com.paytm.tokenvendingservice.request.Credential;

@Aspect
@Configuration
public class Authentication {

  private static final Logger logger = LoggerFactory.getLogger(Authentication.class);

  @Before("@annotation(Authenticate) && execution(* *(..))")
  public void authenticate(JoinPoint joinPoint) throws Throwable {
    logger.info("Authenticate : " + joinPoint.getSignature().getName());
    Object[] args = joinPoint.getArgs();

    for (Object arg : args) {
      Credential reqCredential = null;
      HttpServletRequest servletReq = null;
      if ("com.paytm.tokenvendingservice.request.Credential".equals(arg.getClass().getName())) {
        reqCredential = (Credential) arg;
        logger.info("Class:" + arg.getClass().getName() + " Val:" + reqCredential);
      } else if ("org.apache.catalina.connector.RequestFacade".equals(arg.getClass().getName())) {
        servletReq = (HttpServletRequest) arg;
        logger.info("Class:" + arg.getClass().getName() + " Val:" + servletReq);
      } else {
        logger.info("Class:" + arg.getClass().getName() + " Val:" + arg);
      }
    }
  }

  @Before("@annotation(Authorize) && execution(* *(..))")
  public void authorize(JoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Authorize authorize = method.getAnnotation(Authorize.class);
    logger.info("Authorize : " + signature.getName() + " AuthorizeAnnotation:"
        + Arrays.toString(authorize.roles()));
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      Credential reqCredential = null;
      HttpServletRequest servletReq = null;
      if ("com.paytm.tokenvendingservice.request.Credential".equals(arg.getClass().getName())) {
        reqCredential = (Credential) arg;
        logger.info("Class:" + arg.getClass().getName() + " Val:" + reqCredential);
      } else if ("org.apache.catalina.connector.RequestFacade".equals(arg.getClass().getName())) {
        servletReq = (HttpServletRequest) arg;
        logger.info("Class:" + arg.getClass().getName() + " Val:" + servletReq);
      } else {
        logger.info("Class:" + arg.getClass().getName() + " Val:" + arg);
      }
    }
  }
}

