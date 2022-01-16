package ru.kopranych.rebalancing.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class PortfolioDto {

  private List<PositionDto> positions;
  private BigDecimal cashInSavings;
}
