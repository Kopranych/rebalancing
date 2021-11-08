package ru.kopranych.rebalancing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.kopranych.rebalancing.mapper.PortfolioMapper;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;
import ru.kopranych.rebalancing.model.dto.PortfolioDto;
import ru.kopranych.rebalancing.service.RebalancingPortfolioService;

@RequiredArgsConstructor
@RestController("/api/rebalancing")
public class RebalancingController {

  private final RebalancingPortfolioService rebalancingService;

  @PostMapping
  public Mono<RebalancedPortfolio> rebalancing(@RequestBody Mono<PortfolioDto> portfolio) {
    return portfolio
        .map(PortfolioMapper.INSTANCE::map)
        .map(rebalancingService::rebalancing);
  }

}
