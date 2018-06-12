package com.paytm.tokenvendingservice.response;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Credential {

  @JsonProperty("access_key")
  String accessKey;

  @JsonProperty("secret_key")
  String secretKey;

  @JsonProperty("token")
  String token;

  @JsonProperty("expires_at")
  Date expiresAt;

  public Credential() {
    super();
  }

  public Credential(String accessKey, String secretKey, String token, Date expiresAt) {
    super();
    this.accessKey = accessKey;
    this.secretKey = secretKey;
    this.token = token;
    this.expiresAt = expiresAt;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpirsAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  @Override
  public String toString() {
    return "Credential [accessKey=" + accessKey + ", secretKey=" + secretKey + ", token=" + token
        + ", expiresAt=" + expiresAt + "]";
  }


}
