package ru.kopranych.rebalancing.util;

import static ru.kopranych.rebalancing.util.ValidatorUtils.isNotValidArguments;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.Position;

@Slf4j
@UtilityClass
public class RebalanceCalculator {


  public static BigDecimal getSharePosition(
      final BigDecimal netAssetValue, final BigDecimal positionVolume
  ) {
    if (isNotValidArguments(netAssetValue, positionVolume, "getSharePosition")) {
      return BigDecimal.ZERO;
    }
    if (BigDecimal.ZERO.compareTo(netAssetValue) == 0) {
      log.warn("The divisior is ZERO");
      return BigDecimal.ZERO;
    }
    return positionVolume.divide(netAssetValue, MathContext.DECIMAL64);
  }

  public static BigDecimal getPositionVolume(final BigDecimal amount, final BigDecimal price) {
    if (isNotValidArguments(amount, price, "getPositionVolume")) {
      return BigDecimal.ZERO;
    }
    return amount.multiply(price);
  }

  public static BigDecimal getNetAssetValue(Portfolio portfolio) {
    return portfolio.getPositions()
        .stream()
        .map(Position::getVolume)
        .reduce(BigDecimal::add)
        .orElseGet(() -> {
          log.warn("Empty positions volumes");
          return BigDecimal.ZERO;
        });
  }

  public static BigDecimal getShareDelta(final BigDecimal targetShare, final BigDecimal currentShare) {
    return targetShare.subtract(currentShare);
  }

  public static BigDecimal getVolumeDelta(final BigDecimal shareDelta, final BigDecimal netAssetValue) {
    return shareDelta.multiply(netAssetValue);
  }

  public static BigDecimal getAmountDelta(final BigDecimal volumeDelta, final BigDecimal price) {
    return volumeDelta.divide(price, MathContext.DECIMAL64).setScale(0, RoundingMode.DOWN);
  }

  public static BigDecimal getSplitAmount(final BigDecimal amount, List<BigDecimal> forwardSplit) {
    return forwardSplit.stream()
        .reduce(amount, BigDecimal::multiply);
  }
}
