package ru.kopranych.rebalancing.service.impl;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.Position;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;
import ru.kopranych.rebalancing.model.RebalancedPosition;
import ru.kopranych.rebalancing.service.QuoteService;
import ru.kopranych.rebalancing.service.RebalancingPortfolioService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RebalancingService implements RebalancingPortfolioService {

  private final QuoteService quoteServiceImpl;

  @Override
  public RebalancedPortfolio rebalancing(final Portfolio portfolio) {
    final LocalDate now = LocalDate.now().minusDays(4);
    final var updatedPositions = portfolio
        .getPositions()
        .stream()
        .peek(position -> {
              final var quoteDtoList = quoteServiceImpl.get(position.getTicker(), now, now);
              final var last = quoteDtoList.isEmpty() ? BigDecimal.valueOf(-1) : quoteDtoList.get(0).getLast();
              final var positionVolume = getPositionVolume(position.getAmount(), last);

              position.setVolume(positionVolume);
              position.setPrice(last);
            }
        ).collect(toList());

    final var netAssetValue = getNetAssetValue(new Portfolio(updatedPositions));
    final var rebalancedPositions = updatedPositions.stream()
        .map(position -> {
          final var sharePosition = getSharePosition(netAssetValue, position.getVolume());
          final var shareDelta = getShareDelta(position.getTargetShare(), sharePosition);
          final var volumeDelta = getVolumeDelta(shareDelta, netAssetValue);
          final var amountDelta = getAmountDelta(volumeDelta, position.getPrice());
          final BigDecimal amount = position.getAmount();
          return RebalancedPosition.builder()
              .amountBefore(amount)
              .delta(amountDelta)
              .amountAfter(amount.add(amountDelta))
              .targetShare(position.getTargetShare())
              .currentShare(sharePosition)
              .ticker(position.getTicker())
              .build();
        })
        .collect(toList());

    return new RebalancedPortfolio(rebalancedPositions);
  }

  private BigDecimal getSharePosition(
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

  private BigDecimal getPositionVolume(final BigDecimal amount, final BigDecimal price) {
    if (isNotValidArguments(amount, price, "getPositionVolume")) {
      return BigDecimal.ZERO;
    }
    return amount.multiply(price);
  }

  private BigDecimal getNetAssetValue(Portfolio portfolio) {
    return portfolio.getPositions()
        .stream()
        .map(Position::getVolume)
        .reduce(BigDecimal::add)
        .orElseGet(() -> {
          log.warn("Empty positions volumes");
          return BigDecimal.ZERO;
        });
  }

  private BigDecimal getShareDelta(final BigDecimal targetShare, final BigDecimal currentShare) {
    return targetShare.subtract(currentShare);
  }

  private BigDecimal getVolumeDelta(final BigDecimal shareDelta, final BigDecimal netAssetValue) {
    return shareDelta.multiply(netAssetValue);
  }

  private BigDecimal getAmountDelta(final BigDecimal volumeDelta, final BigDecimal price) {
    return volumeDelta.divide(price, MathContext.DECIMAL64);
  }

  private boolean isNotValidArguments(
      final BigDecimal arg1, final BigDecimal arg2, final String methodName
  ) {
    if (!allNotNull(arg1, arg2)) {
      log.warn(
          "Any of arguments [arg1 {}, arg2 {}] is null for calculated method {}", arg1, arg2,
          methodName
      );
      return true;
    }
    return false;
  }
}
