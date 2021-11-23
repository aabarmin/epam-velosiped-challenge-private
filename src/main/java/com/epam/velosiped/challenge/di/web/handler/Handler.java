package com.epam.velosiped.challenge.di.web.handler;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author Aleksandr Barmin
 */
public interface Handler {
  boolean supports(HttpExchange exchange);

  Object handle(HttpExchange exchange);
}
