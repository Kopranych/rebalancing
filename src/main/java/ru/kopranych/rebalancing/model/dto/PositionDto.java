package ru.kopranych.rebalancing.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class PositionDto {

  private String instrumentId;
  private String ticker;
  private BigDecimal amount;
  private BigDecimal targetShare; // target position share of portfolio example 0.35
  private List<BigDecimal> forwardSplits; //split rate (corporate action) example 3:1

}
