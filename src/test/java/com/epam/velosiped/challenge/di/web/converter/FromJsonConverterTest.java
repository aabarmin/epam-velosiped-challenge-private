package com.epam.velosiped.challenge.di.web.converter;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
class FromJsonConverterTest {
  private FromJsonConverter uut;

  @BeforeEach
  void setUp() {
    uut = new FromJsonConverter();
  }

  @Test
  void convert_fromJson() {
    final String json = """
        {
        "paramA": "10",
        "paramB": "20"
        }
        """;

    final Map<String, String> converted = uut.convert(json);

    assertAll(
        () -> assertTrue(converted.containsKey("paramA")),
        () -> assertTrue(converted.containsKey("paramB")),
        () -> assertEquals("10", converted.get("paramA")),
        () -> assertEquals("20", converted.get("paramB"))
    );
  }
}