package ru.kopranych.rebalancing.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PositionDto {

  private String instrumentId;
  private String ticker;
  private BigDecimal amount;
  private BigDecimal targetShare;

}
