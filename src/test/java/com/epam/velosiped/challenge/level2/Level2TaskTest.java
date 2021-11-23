package com.epam.velosiped.challenge.level2;

import com.epam.velosiped.challenge.level1.Level1Task;
import com.epam.velosiped.challenge.level1.Level1TaskImpl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
class Level2TaskTest {
  private final Level2Task task = new Level2TaskImpl();
  private final int port = 10000 + ThreadLocalRandom.current().nextInt(1000);

  @BeforeEach
  void setUp() throws Exception {
    task.start(port);
  }

  @AfterEach
  void tearDown() {
    task.stop();
  }

  @ParameterizedTest
  @CsvSource({
      "1,2,3",
      "10,20,30"
  })
  void request_shouldCalculateValues(int a, int b, int sum) throws Exception {
    final String url = String.format(
        "http://localhost:%s?a=%s&b=%s",
        port, a, b
    );
    final URLConnection connection = new URL(url).openConnection();
    final String contentType = connection.getContentType();

    assertEquals("text/html", contentType, "Invalid content type");

    final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    final String response = reader.readLine();
    final int responseValue = Integer.parseInt(response);

    assertEquals(sum, responseValue, "Invalid calculation");
  }
}