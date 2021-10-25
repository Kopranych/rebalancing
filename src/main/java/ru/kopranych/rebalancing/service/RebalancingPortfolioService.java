package ru.kopranych.rebalancing.service;

import reactor.core.publisher.Mono;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;

public interface RebalancingPortfolioService {

  Mono<RebalancedPortfolio> rebalancing(Portfolio portfolio);

}
