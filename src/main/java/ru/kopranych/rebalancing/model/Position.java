package ru.kopranych.rebalancing.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Position {

  private String instrumentId;
  private String ticker;
  private BigDecimal amount;
  private BigDecimal targetShare;
  private BigDecimal volume;
  private BigDecimal price;

}
