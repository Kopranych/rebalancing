package ru.kopranych.rebalancing.model;

import java.util.List;
import lombok.Data;

@Data
public class RebalancedPortfolio {

  private List<RebalancedPosition> rebalancedPositions;

}
