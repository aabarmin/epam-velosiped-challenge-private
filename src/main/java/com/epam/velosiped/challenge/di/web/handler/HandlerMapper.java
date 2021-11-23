package com.epam.velosiped.challenge.di.web.handler;

import com.epam.velosiped.challenge.di.web.RequestMapping;
import com.epam.velosiped.challenge.di.Inject;
import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Aleksandr Barmin
 */
public class HandlerMapper {
  @Inject
  private ParameterProvider parameterProvider;

  private final Collection<Handler> handlers = new ArrayList<>();

  public void register(Object handlerCandidate) {
    for (Method method : handlerCandidate.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(RequestMapping.class)) {
        handlers.add(new MethodHandler(
            handlerCandidate,
            method,
            parameterProvider
        ));
      }
    }
  }

  public Optional<Handler> findHandler(final HttpExchange exchange) {
    return handlers.stream()
        .filter(handler -> handler.supports(exchange))
        .findFirst();
  }
}
