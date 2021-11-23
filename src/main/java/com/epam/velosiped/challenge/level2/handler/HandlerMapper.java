package com.epam.velosiped.challenge.level2.handler;

import com.epam.velosiped.challenge.level2.controller.RequestMapping;
import com.epam.velosiped.challenge.level5.bean.annotation.Inject;
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
  private ParameterExtractor parameterExtractor;

  private final Collection<Handler> handlers = new ArrayList<>();

  public void register(Object handlerCandidate) {
    for (Method method : handlerCandidate.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(RequestMapping.class)) {
        handlers.add(new MethodHandler(
            handlerCandidate,
            method,
            parameterExtractor
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
