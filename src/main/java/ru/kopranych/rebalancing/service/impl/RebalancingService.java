package ru.kopranych.rebalancing.service.impl;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.RebalancedPortfolio;
import ru.kopranych.rebalancing.service.RebalancingPortfolioService;

@Slf4j
@Service
public class RebalancingService implements RebalancingPortfolioService {

  @Override
  public Mono<RebalancedPortfolio> rebalancing(final Portfolio portfolio) {
    return Mono.just(new RebalancedPortfolio());
  }

  private BigDecimal getSharePosition(final BigDecimal netAssetValue, final BigDecimal positionVolume) {
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
    return null;
  }

  private boolean isNotValidArguments(
      final BigDecimal arg1, final BigDecimal arg2, final String methodName
  ) {
    if (!allNotNull(arg1, arg2)) {
      log.warn(
          "Any of arguments [arg1 {}, arg2 {}] is null for calculated method {}", arg1, arg2, methodName
      );
      return true;
    }
    return false;
  }

//  "https://api.iextrading.com/1.0/ref-data/symbols"
//  "https://api.iextrading.com/1.0/stock/${ticker}/quote"
//  "483JGYAIRT8FQ3QF"
}
