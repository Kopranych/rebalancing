package ru.kopranych.rebalancing.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Portfolio {

  private List<Position> positions;
}
