package ru.kopranych.rebalancing.model;

import java.util.List;
import lombok.Data;

@Data
public class Portfolio {

  private List<Position> positions;
}
