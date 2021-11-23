package com.epam.velosiped.challenge.level2.handler;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author Aleksandr Barmin
 */
public class ParameterExtractor {
  public String queryParameter(HttpExchange exchange, String name) {
    final String query = exchange.getRequestURI().getQuery();
    final String toFind = name + "=";
    String value = query.substring(query.indexOf(toFind) + toFind.length());
    if (value.indexOf("&") != -1) {
      value = value.substring(0, value.indexOf("&"));
    }
    return value;
  }
}
