package ru.kopranych.rebalancing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.kopranych.rebalancing.model.Portfolio;
import ru.kopranych.rebalancing.model.dto.PortfolioDto;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PortfolioMapper {

  PortfolioMapper INSTANCE = Mappers.getMapper(PortfolioMapper.class);

  Portfolio map(PortfolioDto portfolioDto);

}
