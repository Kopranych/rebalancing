package ru.kopranych.rebalancing.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import ru.kopranych.rebalancing.model.dto.QuoteDto;

@Slf4j
@UtilityClass
public class CsvParserUtils {

  private static final String TRADEDATE = "TRADEDATE";
  private static final String SECID = "SECID";
  private static final String LEGALCLOSEPRICE = "LEGALCLOSEPRICE";
  private static final String CLOSE = "CLOSE";
  private static final String OPEN = "OPEN";
  private static final String LOW = "LOW";
  private static final String HIGH = "HIGH";
  private static final String MARKETPRICE2 = "MARKETPRICE2";
  private static final String MARKETPRICE3 = "MARKETPRICE3";
  private static final String ADMITTEDQUOTE = "ADMITTEDQUOTE";
  private static final List<String> FIELD_PRICE_ARRAY = List
      .of(MARKETPRICE2, MARKETPRICE3, ADMITTEDQUOTE);

  public QuoteDto buildQuote(Map<String, Integer> headersMap, CSVRecord rowCurr) {
    final var price = getPrice(headersMap, rowCurr);

    return new QuoteDto(
        rowCurr.get(headersMap.get(SECID)),
        LocalDate.parse(rowCurr.get(headersMap.get(TRADEDATE))),
        price
    );
  }

  private BigDecimal getPrice(Map<String, Integer> headersMap, CSVRecord rowCurr) {
    return FIELD_PRICE_ARRAY
        .stream()
        .map(headersMap::get)
        .map(rowCurr::get)
        .filter(price -> !StringUtils.isBlank(price))
        .map(BigDecimal::new)
        .findFirst()
        .orElseGet(() -> {
          log.warn("Not found price for rowCurr {}", rowCurr);
          return BigDecimal.ZERO;
        });
  }
}
