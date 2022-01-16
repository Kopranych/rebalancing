package ru.kopranych.rebalancing.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getPositionVolume;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getShareDelta;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getSharePosition;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getSplitAmount;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getVolumeDelta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.jupiter.api.Test;

class RebalanceCalculatorTest {

  @Test
  void shouldCorrectCalculateVolume() {
    //given
    final var amount = BigDecimal.valueOf(3);
    final var price = BigDecimal.valueOf(10930);
    //when
    final var actual = getPositionVolume(amount, price);
    //then
    final var expected = BigDecimal.valueOf(32790);
    assertEquals(expected, actual);
  }

  @Test
  void shouldCorrectCalculateShare() {
    //given
    final var netAssetValue = BigDecimal.valueOf(220766);
    final var positionVolume = BigDecimal.valueOf(32790);
    //when
    final var actual = getSharePosition(netAssetValue, positionVolume);
    //then
    final var expected = BigDecimal.valueOf(0.1485);
    assertEquals(expected, actual.setScale(4, RoundingMode.DOWN));
  }

  @Test
  void shouldCorrectCalculateShareDelta() {
    //given
    final var targetShare = BigDecimal.valueOf(28);
    final var currentShare = BigDecimal.valueOf(14.85);
    //when
    final var actual = getShareDelta(targetShare, currentShare);
    //then
    final var expected = BigDecimal.valueOf(13.15);
    assertEquals(expected, actual);
  }

  @Test
  void shouldCorrectCalculateVolumeDelta() {
    //given
    final var shareDelta = BigDecimal.valueOf(0.1315);
    final var netAssetValue = BigDecimal.valueOf(220766);
    //when
    final var actual = getVolumeDelta(shareDelta, netAssetValue);
    //then
    final var expected = new BigDecimal("29030.7290");
    assertEquals(expected, actual);
  }

  @Test
  void shouldCorrectCalculateForwardSplit() {
    //given
    var amount = BigDecimal.valueOf(10);
    var splits = List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(2), BigDecimal.valueOf(100));
    //when
    var actual = getSplitAmount(amount, splits);
    //then
    assertEquals(BigDecimal.valueOf(10000), actual);
  }

}