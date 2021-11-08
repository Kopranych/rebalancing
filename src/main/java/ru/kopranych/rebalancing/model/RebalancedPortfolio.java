package ru.kopranych.rebalancing.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RebalancedPortfolio {

  private List<RebalancedPosition> rebalancedPositions;

}
