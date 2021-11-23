package com.epam.velosiped.challenge.level2.controller;

import com.epam.velosiped.challenge.di.web.Controller;
import com.epam.velosiped.challenge.di.web.HttpMethod;
import com.epam.velosiped.challenge.di.web.QueryParameter;
import com.epam.velosiped.challenge.di.web.RequestMapping;
import com.epam.velosiped.challenge.level2.service.VelosipedCalculationService;
import com.epam.velosiped.challenge.di.Inject;

/**
 * @author Aleksandr Barmin
 */
@Controller
public class VelosipedCalculationController {
  @Inject
  private VelosipedCalculationService calculationService;

  @RequestMapping(method = HttpMethod.GET, path = "/")
  public int calculate(@QueryParameter("a") String a,
                       @QueryParameter("b") String b) {

    return calculationService.sum(
        Integer.parseInt(a),
        Integer.parseInt(b)
    );
  }
}
