package com.paytm.tokenvendingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApp {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(ServerApp.class, args);
  }
}
// mvn clean
// mvn -Dserver package
// java -jar target/server-boilerplate-0.0.1-SNAPSHOT.jar
