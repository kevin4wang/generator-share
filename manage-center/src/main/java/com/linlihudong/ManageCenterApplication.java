package com.linlihudong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class ManageCenterApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManageCenterApplication.class, args);
  }
}
