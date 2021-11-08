package ru.kopranych.rebalancing.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "quote", url = "https://iss.moex.com/iss/history/engines/stock/markets/shares/securities")
public interface QuoteClient {

  @GetMapping("/{ticker}.csv")
  Mono<String> getQuotes(@PathVariable String ticker, @RequestParam Integer start);

  @GetMapping("/{ticker}.csv")
  Mono<String> getQuotes(
      @PathVariable String ticker,
      @RequestParam String from,
      @RequestParam String to,
      @RequestParam Integer start
  );
}
