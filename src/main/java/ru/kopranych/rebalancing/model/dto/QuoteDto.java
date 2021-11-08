package ru.kopranych.rebalancing.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuoteDto {

  private String ticker;
  private LocalDate date;
  private BigDecimal last;
}
