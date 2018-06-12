package com.paytm.tokenvendingservice.service;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.paytm.tokenvendingservice.exception.AppException;
import com.paytm.tokenvendingservice.profiler.LogTime;
import com.paytm.tokenvendingservice.response.Credential;

@Service
public class CredentialService {
  private static final Logger logger = LoggerFactory.getLogger(CredentialService.class);

  @Autowired
  private Environment env;

  @Value("${aws.access_key}")
  private String awsAccessKey;

  @Value("${aws.secret_key}")
  private String awsSecretKey;

  @LogTime
  public Credential create(HttpServletRequest servletReq,
      com.paytm.tokenvendingservice.request.Credential request) {
    try {
      BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
      AWSSecurityTokenServiceClient sts_client = new AWSSecurityTokenServiceClient(awsCreds);
      sts_client.setEndpoint("sts.ap-south-1.amazonaws.com");
      GetSessionTokenRequest session_token_request = new GetSessionTokenRequest();
      session_token_request.setDurationSeconds(request.getDurationInSeconds());
      GetSessionTokenResult session_token_result =
          sts_client.getSessionToken(session_token_request);
      Credentials awsCredentials = session_token_result.getCredentials();
      return new Credential(awsCredentials.getAccessKeyId(), awsCredentials.getSecretAccessKey(),
          awsCredentials.getSessionToken(), awsCredentials.getExpiration());
    } catch (Exception ex) {
      logger.error("Exception : " + ex.getMessage());
      throw new AppException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
