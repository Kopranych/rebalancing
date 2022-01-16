package ru.kopranych.rebalancing.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Portfolio {

  private List<Position> positions;
  private BigDecimal cashInSavings;
}
