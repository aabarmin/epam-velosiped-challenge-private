package com.epam.velosiped.challenge.di.web.converter;

import com.epam.velosiped.challenge.level3.CalculationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
class ToJsonConverterTest {
  private ToJsonConverter uut;

  @BeforeEach
  void setUp() {
    uut = new ToJsonConverter();
  }

  @Test
  void convert_convertingObjectToJson() {
    final CalculationResponse response = new CalculationResponse();
    response.setValue("1234");

    final String result = uut.convert(response);

    assertEquals("{\"value\": \"1234\"}", result);
  }
}