package com.paytm.tokenvendingservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceResponse<T> extends ResponseEntity<T> {

  public ServiceResponse(T data, HttpStatus status) {
    super(data, status);
  }
}
