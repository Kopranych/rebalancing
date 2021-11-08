package ru.kopranych.rebalancing.service;

import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;

public interface RebalancingPortfolioService {

  RebalancedPortfolio rebalancing(Portfolio portfolio);

}
