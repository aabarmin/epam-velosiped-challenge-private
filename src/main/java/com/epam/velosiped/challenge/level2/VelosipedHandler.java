package com.epam.velosiped.challenge.level2;

import com.epam.velosiped.challenge.common.VelosipedResponse;
import com.epam.velosiped.challenge.level2.controller.VelosipedCalculationController;
import com.epam.velosiped.challenge.level2.handler.Handler;
import com.epam.velosiped.challenge.level2.handler.HandlerMapper;
import com.epam.velosiped.challenge.level5.bean.annotation.Inject;
import com.epam.velosiped.challenge.level5.post.processor.PostConstruct;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksandr Barmin
 */
public class VelosipedHandler implements HttpHandler {
  @Inject
  private HandlerMapper handlerMapper;

  @Inject
  private VelosipedCalculationController controller;

  @PostConstruct
  public void init() {
    handlerMapper.register(controller);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    final Handler handler = handlerMapper.findHandler(exchange).orElseThrow();
    try {
      final Object response = handler.handle(exchange);
      writeResponse(exchange, new VelosipedResponse(200, String.valueOf(response)));
    } catch (Exception e) {
      writeResponse(exchange, new VelosipedResponse(500, "Unexpected error"));
    }
  }

  private void writeResponse(HttpExchange exchange, VelosipedResponse response) throws IOException {
    try (final OutputStream responseBody = exchange.getResponseBody()) {
      for (Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
        exchange.getResponseHeaders().put(entry.getKey(), entry.getValue());
      }
      exchange.sendResponseHeaders(response.getStatus(), response.getResponseLength());
      responseBody.write(response.getResponse());
    }
  }
}
