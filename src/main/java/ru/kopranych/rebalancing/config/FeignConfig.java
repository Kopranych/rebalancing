package ru.kopranych.rebalancing.config;

import feign.Contract;
import feign.FeignException;
import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.ReactiveContract;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients(basePackages = "ru.kopranych.rebalancing")
public class FeignConfig {

  @Bean
  public Contract contract() {
    return new ReactiveContract(new SpringMvcContract());
  }

  @Bean
  public Logger logger() {
    return new Slf4jLogger();
  }

  @Bean
  public Logger.Level loggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return FeignException::errorStatus;
  }
}
