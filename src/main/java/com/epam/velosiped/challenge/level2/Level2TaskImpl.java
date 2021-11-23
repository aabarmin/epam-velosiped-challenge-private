package com.epam.velosiped.challenge.level2;

import com.epam.velosiped.challenge.level2.controller.VelosipedCalculationController;
import com.epam.velosiped.challenge.level2.handler.HandlerMapper;
import com.epam.velosiped.challenge.level2.handler.ParameterExtractor;
import com.epam.velosiped.challenge.level2.service.VelosipedCalculationService;
import com.epam.velosiped.challenge.level5.ApplicationContext;
import com.epam.velosiped.challenge.level5.ConfigurableApplicationContext;
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
      ParameterExtractor.class,
      VelosipedCalculationController.class,
      VelosipedCalculationService.class
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
