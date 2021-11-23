package com.epam.velosiped.challenge.level2.controller;

import com.epam.velosiped.challenge.level2.service.VelosipedCalculationService;
import com.epam.velosiped.challenge.level5.bean.annotation.Inject;

/**
 * @author Aleksandr Barmin
 */
public class VelosipedCalculationController {
  @Inject
  private VelosipedCalculationService calculationService;

  @RequestMapping(method = "get", path = "/")
  public int calculate(@QueryParameter("a") String a,
                       @QueryParameter("b") String b) {

    return calculationService.sum(
        Integer.parseInt(a),
        Integer.parseInt(b)
    );
  }
}
