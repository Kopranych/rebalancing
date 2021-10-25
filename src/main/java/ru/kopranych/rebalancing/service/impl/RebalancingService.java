package ru.kopranych.rebalancing.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;
import ru.kopranych.rebalancing.service.RebalancingPortfolioService;

@Service
public class RebalancingService implements RebalancingPortfolioService {

  @Override
  public Mono<RebalancedPortfolio> rebalancing(final Portfolio portfolio) {
    return Mono.just(new RebalancedPortfolio());
  }
}
