package ru.kopranych.rebalancing.service;

import java.time.LocalDate;
import java.util.List;
import ru.kopranych.rebalancing.model.dto.QuoteDto;

public interface QuoteService {
  QuoteDto get(final String ticker);
  List<QuoteDto> get(final String ticker, final LocalDate dateFrom, final LocalDate dateTo);
}
