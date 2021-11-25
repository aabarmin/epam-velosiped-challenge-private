package com.epam.velosiped.challenge.level2.controller;

import com.epam.velosiped.challenge.di.web.Controller;
import com.epam.velosiped.challenge.di.web.HttpMethod;
import com.epam.velosiped.challenge.di.web.QueryParameter;
import com.epam.velosiped.challenge.di.web.RequestBody;
import com.epam.velosiped.challenge.di.web.RequestMapping;
import com.epam.velosiped.challenge.level2.service.VelosipedCalculationService;
import com.epam.velosiped.challenge.di.Inject;
import com.epam.velosiped.challenge.level3.CalculationRequest;
import com.epam.velosiped.challenge.level3.CalculationResponse;

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

  @RequestMapping(method = HttpMethod.POST, path = "/")
  public CalculationResponse calculate(@RequestBody CalculationRequest request) {
    // some code here
    final int result = calculationService.sum(
        Integer.parseInt(request.getParamA()),
        Integer.parseInt(request.getParamB())
    );
    final CalculationResponse response = new CalculationResponse();
    response.setValue(String.valueOf(result));
    return response;
  }
}
