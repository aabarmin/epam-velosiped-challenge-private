package com.epam.velosiped.challenge.level2;

import com.epam.velosiped.challenge.di.ApplicationContext;
import com.epam.velosiped.challenge.di.ConfigurableApplicationContext;
import com.epam.velosiped.challenge.di.web.converter.FromJsonConverter;
import com.epam.velosiped.challenge.di.web.converter.ToJsonConverter;
import com.epam.velosiped.challenge.di.web.handler.HandlerMapper;
import com.epam.velosiped.challenge.di.web.handler.ParameterProvider;
import com.epam.velosiped.challenge.di.web.handler.extractor.QueryParameterExtractor;
import com.epam.velosiped.challenge.di.web.handler.extractor.RequestBodyExtractor;
import com.epam.velosiped.challenge.di.web.response.ResponseConverter;
import com.epam.velosiped.challenge.level2.controller.VelosipedCalculationController;
import com.epam.velosiped.challenge.level2.service.VelosipedCalculationService;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author Aleksandr Barmin
 */
public class Level2TaskImpl implements Level2Task {
  private final ApplicationContext applicationContext = new ConfigurableApplicationContext(
      VelosipedHandler.class,
      HandlerMapper.class,
      ParameterProvider.class,
      VelosipedCalculationController.class,
      VelosipedCalculationService.class,
      QueryParameterExtractor.class,
      ResponseConverter.class,
      ToJsonConverter.class,
      FromJsonConverter.class,
      RequestBodyExtractor.class
  );

  private HttpServer httpServer;

  @Override
  public void start(int port) throws Exception {
    httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    httpServer.createContext("/", applicationContext.getBean(VelosipedHandler.class));
    httpServer.setExecutor(Executors.newSingleThreadExecutor());
    httpServer.start();
  }

  @Override
  public void stop() {
    httpServer.stop(1);
  }
}
