package com.paytm.tokenvendingservice.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.paytm.tokenvendingservice.auth.Authenticate;
import com.paytm.tokenvendingservice.auth.Authorize;
import com.paytm.tokenvendingservice.exception.AppException;
import com.paytm.tokenvendingservice.response.ServiceResponse;
import com.paytm.tokenvendingservice.service.CredentialService;

@RestController
@RequestMapping(value = "/credential")
public class CredentialController {

  private static final Logger logger = LoggerFactory.getLogger(CredentialController.class);

  @Autowired
  CredentialService credentialService;

  @Authenticate
  @Authorize(roles = {"ImageUploader"})
  @RequestMapping(method = RequestMethod.POST)
  public ServiceResponse<com.paytm.tokenvendingservice.response.Credential> create(
      HttpServletRequest servletReq, @RequestBody com.paytm.tokenvendingservice.request.Credential request)
      throws AppException {
    return new ServiceResponse<com.paytm.tokenvendingservice.response.Credential>(
        credentialService.create(servletReq, request), HttpStatus.OK);
  }

}
