package ru.kopranych.rebalancing.model;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public class RebalancedPosition {

  private String instrumentId;
  private String ticker;
  private BigDecimal amountBefore;
  private BigDecimal amountAfter;
  private BigDecimal delta;
  private BigDecimal targetShare;
  private BigDecimal currentShare;

}
