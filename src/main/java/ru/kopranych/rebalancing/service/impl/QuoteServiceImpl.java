package ru.kopranych.rebalancing.service.impl;

import static java.lang.String.format;
import static java.util.Collections.emptyList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kopranych.rebalancing.client.QuoteClient;
import ru.kopranych.rebalancing.model.dto.QuoteDto;
import ru.kopranych.rebalancing.model.exception.NotFoundException;
import ru.kopranych.rebalancing.service.CsvParser;
import ru.kopranych.rebalancing.service.QuoteService;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

  private static final int PAGE_SIZE = 100;
  private final QuoteClient quoteClient;
  private final CsvParser csvParser;

  @SneakyThrows
  @Override
  public QuoteDto get(final String ticker) {
    var date = LocalDate.now();
    final var maxPastDaysCount = 10;
    var count = 0;

    do {
      final var quotes = quoteClient
          .getQuotes(ticker, date.toString(), date.toString(), 0)
          .map(this::parseQuotes)
          .toFuture().get();

      if (!quotes.isEmpty()) {
        return quotes.get(0);
      }
      count++;
      date = date.minusDays(count);
    } while (count < maxPastDaysCount);

    throw new NotFoundException(format("Quotes not found for instrument %s", ticker));
  }

  @SneakyThrows
  @Override
  public List<QuoteDto> get(final String ticker, final LocalDate dateFrom, final LocalDate dateTo) {
    var index = 0;
    var total = 0;
    var quoteList = new ArrayList<QuoteDto>();
    do {
      total = quoteClient.getQuotes(ticker, dateFrom.toString(), dateTo.toString(), index)
          .map(quotesResponse -> {
            final var parseQuotes = parseQuotes(quotesResponse);
            quoteList.addAll(parseQuotes);
            return parseTotal(quotesResponse);
          }).toFuture().get();
      index += PAGE_SIZE;
    } while (index < total);
    return quoteList;
  }

  private Integer parseTotal(final String quotesResponse) {
    var pageIndex = quotesResponse.substring(
        quotesResponse.indexOf("INDEX")
    );
    try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(
        pageIndex.getBytes(StandardCharsets.UTF_8))) {
      return csvParser.getTotalIndex(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private List<QuoteDto> parseQuotes(final String quotesResponse) {
    var quotes = quotesResponse.substring(
        quotesResponse.indexOf("BOARDID"), quotesResponse.indexOf("history.cursor") - 1
    );
    try (var inputStream = new ByteArrayInputStream(quotes.getBytes(StandardCharsets.UTF_8))) {
      return csvParser.parseQuotes(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.warn("Empty quotes");
    return emptyList();
  }
}
