package ru.kopranych.rebalancing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;
import ru.kopranych.rebalancing.service.RebalancingPortfolioService;

@RequiredArgsConstructor
@RestController("/api/rebalancing")
public class RebalancingController {

  private final RebalancingPortfolioService rebalancingService;

  @PostMapping
  public Mono<RebalancedPortfolio> rebalancing(Portfolio portfolio) {
    return rebalancingService.rebalancing(portfolio);
  }

}
