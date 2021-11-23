package com.epam.velosiped.challenge.level1;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksandr Barmin
 */
public class VelosipedHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    final VelosipedResponse response;
    if (isSupported(exchange)) {
      response = createSuccessResponse(exchange);
    } else {
      response = create500Response();
    }
    writeResponse(exchange, response);
  }

  private VelosipedResponse createSuccessResponse(HttpExchange exchange) {
    final int first = Integer.parseInt(getQueryParameter(exchange, "a"));
    final int second = Integer.parseInt(getQueryParameter(exchange, "b"));
    return new VelosipedResponse(200, String.valueOf(first + second));
  }

  private VelosipedResponse create500Response() {
    return new VelosipedResponse(500, "Unsupported request");
  }

  private boolean isSupported(HttpExchange exchange) {
    final URI requestURI = exchange.getRequestURI();
    return exchange.getRequestMethod().equalsIgnoreCase("get") &&
        hasQueryParameter(requestURI, "a") &&
        hasQueryParameter(requestURI, "b");
  }

  private String getQueryParameter(HttpExchange exchange, String parameter) {
    return getQueryParameter(exchange.getRequestURI(), parameter);
  }

  private String getQueryParameter(URI requestURI, String parameter) {
    final String query = requestURI.getQuery();
    String value = query.substring(query.indexOf(parameter + "=") + parameter.length() + 1);
    if (value.indexOf("&") != -1) {
      value = value.substring(0, value.indexOf("&"));
    }
    return value;
  }

  private boolean hasQueryParameter(URI requestURI, String parameter) {
    final String query = requestURI.getQuery();
    return query.contains(parameter + "=");
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
