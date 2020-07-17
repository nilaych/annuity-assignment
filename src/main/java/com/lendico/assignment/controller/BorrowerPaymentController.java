package com.lendico.assignment.controller;

import com.lendico.assignment.model.BorrowerPlanRequest;
import com.lendico.assignment.model.BorrowerPlanResponse;
import com.lendico.assignment.service.BorrowerPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowerPaymentController {

    @Autowired
    private BorrowerPaymentService borrowerPaymentService;

    @PostMapping("/generate-plan")
    public BorrowerPlanResponse generatePlan(@RequestBody BorrowerPlanRequest request) {
        return new BorrowerPlanResponse(borrowerPaymentService.generatePlan(request));
    }
}
