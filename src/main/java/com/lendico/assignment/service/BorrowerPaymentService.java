package com.lendico.assignment.service;

import com.lendico.assignment.model.BorrowerPayment;
import com.lendico.assignment.model.BorrowerPlanRequest;
import com.lendico.assignment.service.helper.BorrowerPaymentCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BorrowerPaymentService {

    @Autowired
    private BorrowerPaymentCalculator borrowerPaymentCalculator;

    public List<BorrowerPayment> generatePlan(BorrowerPlanRequest request) {
        List<BorrowerPayment> payments = borrowerPaymentCalculator.calculate(
                request.getLoanAmount(),
                request.getNominalRate(),
                request.getDuration(),
                request.getStartDate());

        payments.set(payments.size() - 1, adjustLastMonthPayment(payments.get(payments.size() - 1)));
        return payments;
    }

    private BorrowerPayment adjustLastMonthPayment(BorrowerPayment paymentDetails) {
        BorrowerPayment adjustedPayment = paymentDetails;
        double remainingOutstandingPrincipal = paymentDetails.getRemainingOutstandingPrincipal();

        log.info("Adjusting last month payment for extra amount {} (if any)", remainingOutstandingPrincipal);
        log.info("Actual calculated payment {}", paymentDetails);

        if(remainingOutstandingPrincipal > 0) {
            double annuity = paymentDetails.getBorrowerPaymentAmount() - remainingOutstandingPrincipal;
            adjustedPayment =  BorrowerPayment.builder()
                    .borrowerPaymentAmount(annuity)
                    .date(paymentDetails.getDate())
                    .initialOutstandingPrincipal(annuity - paymentDetails.getInterest())
                    .interest(paymentDetails.getInterest())
                    .principal(paymentDetails.getPrincipal() - remainingOutstandingPrincipal)
                    .remainingOutstandingPrincipal(paymentDetails.getRemainingOutstandingPrincipal() - remainingOutstandingPrincipal)
                    .build();
        }

        log.info("Adjusted last payment {}", adjustedPayment);
        return adjustedPayment;
    }
}
