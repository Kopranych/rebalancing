package ru.kopranych.rebalancing.util;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ValidatorUtils {

  public static boolean isNotValidArguments(
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
