package com.paytm.tokenvendingservice;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.paytm.tokenvendingservice.exception.AppException;

@EnableWebMvc
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  ResponseEntity<Object> handleControllerException(HttpServletRequest request, Throwable ex) {
    Map<String, String> responseBody = new HashMap<String, String>();
    if (ex instanceof AppException) {
      AppException appException = (AppException) ex;
      logger.error("AppException : " + "\"" + appException.getMessage() + "\"" + " HttpStatusCode:"
          + "\"" + appException.getHttpStatus() + "\"");
      responseBody.put("message", appException.getMessage());
      ResponseEntity<Object> response =
          new ResponseEntity<Object>(responseBody, appException.getHttpStatus());
      return response;
    }
    responseBody.put("message", "Unhandled");
    Utility.logException(logger, Level.ERROR, ex);
    return new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    // TODO:log this
    Map<String, String> responseBody = new HashMap<String, String>();
    responseBody.put("path", request.getContextPath());
    responseBody.put("message", "The URL you have reached is not in service at this time (404).");
    return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
  }

}
