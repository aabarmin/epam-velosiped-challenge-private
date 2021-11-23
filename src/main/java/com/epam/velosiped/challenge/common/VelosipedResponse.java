package com.epam.velosiped.challenge.common;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksandr Barmin
 */
public class VelosipedResponse {
  private final int status;
  private final byte[] response;
  private final Map<String, List<String>> headers = new HashMap<>();

  public VelosipedResponse(int status, byte[] response) {
    this.status = status;
    this.response = response;
    this.headers.put("Content-Type", Lists.of("text/html"));
  }

  public VelosipedResponse(int status, String response) {
    this(status, response.getBytes(StandardCharsets.UTF_8));
  }

  public Map<String, List<String>> getHeaders() {
    return headers;
  }

  public byte[] getResponse() {
    return response;
  }

  public int getStatus() {
    return status;
  }

  public int getResponseLength() {
    return response.length;
  }
}
