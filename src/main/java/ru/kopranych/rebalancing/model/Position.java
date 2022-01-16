package ru.kopranych.rebalancing.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class Position {

  private String instrumentId;
  private String ticker;
  private BigDecimal amount;
  private BigDecimal targetShare;
  private BigDecimal volume;
  private BigDecimal price;
  private List<BigDecimal> forwardSplits; //split rate (corporate action) example 3:1

}
