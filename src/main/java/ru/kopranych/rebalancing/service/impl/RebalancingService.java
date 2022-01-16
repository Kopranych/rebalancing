package ru.kopranych.rebalancing.service.impl;

import static ru.kopranych.rebalancing.util.RebalanceCalculator.getAmountDelta;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getNetAssetValue;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getPositionVolume;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getShareDelta;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getSharePosition;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getSplitAmount;
import static ru.kopranych.rebalancing.util.RebalanceCalculator.getVolumeDelta;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kopranych.rebalancing.model.Portfolio;
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
    final var updatedPositions = portfolio
        .getPositions()
        .stream()
        .peek(position -> {
              final var quote = quoteServiceImpl.get(position.getTicker());
              final var last = quote.getLast();
              final var splitAmount =
                  getSplitAmount(position.getAmount(), position.getForwardSplits());
              final var positionVolume = getPositionVolume(splitAmount, last);
              position.setAmount(splitAmount);
              position.setVolume(positionVolume);
              position.setPrice(last);
            }
        ).toList();

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
        .toList();

    return new RebalancedPortfolio(rebalancedPositions);
  }

}
