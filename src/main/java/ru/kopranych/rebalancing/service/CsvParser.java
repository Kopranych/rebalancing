package ru.kopranych.rebalancing.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static ru.kopranych.rebalancing.util.StreamUtils.distinctByKey;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.kopranych.rebalancing.model.dto.QuoteDto;
import ru.kopranych.rebalancing.util.CsvParserUtils;

@Slf4j
@Service
public class CsvParser {

  private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withDelimiter(';').withHeader();


  public List<QuoteDto> parseQuotes(final InputStream inputStream) {
    try (final var reader = new InputStreamReader(inputStream)) {
      final CSVParser parser = new CSVParser(reader, CSV_FORMAT);
      return processQuotes(parser);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
  }

  private Stream<CSVRecord> makeStream(final CSVParser parser) {
    return StreamSupport.stream(parser.spliterator(), false);
  }

  private List<QuoteDto> processQuotes(final CSVParser parser) {
    var headersMap = parser.getHeaderMap();
    return makeStream(parser)
        .map( row ->  CsvParserUtils.buildQuote(headersMap, row))
        .filter(quote -> quote.getLast() != null )
        .filter(distinctByKey(QuoteDto::getDate))
        .collect(toList());
  }


  public Integer getTotalIndex(final InputStream inputStream) {
    try (final var reader = new InputStreamReader(inputStream)){
      final CSVParser parser = new CSVParser(reader, CSV_FORMAT);
      return getTotalIndex(parser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  private Integer getTotalIndex(final CSVParser parser){
    var headersMap = parser.getHeaderMap();
    return makeStream(parser)
        .map ( row -> {
          final var total = headersMap.get("TOTAL");
          return Integer.valueOf(row.get(total));
        })
        .findFirst()
        .orElseGet(() -> {
          log.warn("Not found any index");
          return -1;
        });
  }
}
