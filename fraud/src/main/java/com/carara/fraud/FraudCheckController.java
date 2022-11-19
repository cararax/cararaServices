package com.carara.fraud;

import com.carara.fraud.model.Response.FraudCheckResponse;
import com.carara.fraud.service.FraudCheckService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud-check")
@AllArgsConstructor
public class FraudCheckController {

    public final FraudCheckService fraudCheckService;

    @GetMapping(path = "/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {

        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);

        return new FraudCheckResponse(isFraudulentCustomer);
    }

}
