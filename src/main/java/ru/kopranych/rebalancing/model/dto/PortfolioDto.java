package ru.kopranych.rebalancing.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class PortfolioDto {

  private List<PositionDto> positions;
}
