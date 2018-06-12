package com.paytm.tokenvendingservice.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
  private static final long serialVersionUID = -7123945468944524888L;
  HttpStatus httpStatus;

  public AppException(HttpStatus httpStatus) {
    super();
    this.httpStatus = httpStatus;
  }

  public AppException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

}
