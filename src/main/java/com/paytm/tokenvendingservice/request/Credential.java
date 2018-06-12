package com.paytm.tokenvendingservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credential {
  @JsonProperty("duration_in_seconds")
  int durationInSeconds;

  public Credential() {
    super();
  }

  public Credential(int durationInSeconds) {
    super();
    this.durationInSeconds = durationInSeconds;
  }

  public int getDurationInSeconds() {
    return durationInSeconds;
  }

  public void setDurationInSeconds(int durationInSeconds) {
    this.durationInSeconds = durationInSeconds;
  }

  @Override
  public String toString() {
    return "Credential [durationInSeconds=" + durationInSeconds + "]";
  }

}
