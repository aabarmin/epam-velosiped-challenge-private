package com.epam.velosiped.challenge.level1;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author Aleksandr Barmin
 */
public class Level1TaskImpl implements Level1Task {
  private HttpServer httpServer;

  @Override
  public void start(int port) throws Exception {
    httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    httpServer.createContext("/", new VelosipedHandler());
    httpServer.setExecutor(Executors.newSingleThreadExecutor());
    httpServer.start();
  }

  @Override
  public void stop() {
    httpServer.stop(1);
  }
}
