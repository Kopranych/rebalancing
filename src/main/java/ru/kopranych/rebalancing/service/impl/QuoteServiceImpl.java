package ru.kopranych.rebalancing.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kopranych.rebalancing.client.QuoteClient;
import ru.kopranych.rebalancing.model.dto.QuoteDto;
import ru.kopranych.rebalancing.service.CsvParser;
import ru.kopranych.rebalancing.service.QuoteService;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

  private static final int PAGE_SIZE = 100;
  private final QuoteClient quoteClient;
  private final CsvParser csvParser;

  @Override
  public QuoteDto get(final String ticker) {
    return null;
  }

  @Override
  public List<QuoteDto> get(final String ticker, final LocalDate dateFrom, final LocalDate dateTo) {
    var index = 0;
    var total = 0;
    var quoteList = new ArrayList<QuoteDto>();
    do {
      total = quoteClient.getQuotes(ticker, dateFrom.toString(), dateTo.toString(), index)
          .map(quotesResponse -> {
            var quotes = quotesResponse.substring(
                quotesResponse.indexOf("BOARDID"), quotesResponse.indexOf("history.cursor") - 1
            );
            try(var inputStream = new ByteArrayInputStream(quotes.getBytes(StandardCharsets.UTF_8))) {
              var parseQuotes = csvParser.parseQuotes(inputStream);
              quoteList.addAll(parseQuotes);
            } catch (IOException e) {
              e.printStackTrace();
            }
            var pageIndex = quotesResponse.substring(
                quotesResponse.indexOf("INDEX")
            );
            try(final ByteArrayInputStream inputStream = new ByteArrayInputStream(pageIndex.getBytes(StandardCharsets.UTF_8))) {
              return csvParser.getTotalIndex(inputStream);
            } catch (IOException e) {
              e.printStackTrace();
            }
            return -1;
          }).toProcessor().block();

      index += PAGE_SIZE;
    } while (index < total);
    return quoteList;
  }
}
