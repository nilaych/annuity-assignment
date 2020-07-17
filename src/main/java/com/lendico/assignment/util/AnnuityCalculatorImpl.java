package com.lendico.assignment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AnnuityCalculatorImpl implements AnnuityCalculator {

    public static final int DAYS_OF_MONTH = 30;
    public static final int DAYS_OF_YEAR = 360;

    @Override
    public double calculateAnnuity(double loanAmount, float monthlyInterestRate, int duration) {

        double monthlyAnnuity =  (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -duration));

        if(log.isDebugEnabled()) {
            log.debug("Annuity for {} with {}% monthly interest for {} months is {}",
                    loanAmount, monthlyInterestRate, duration, monthlyAnnuity);
        }

        return monthlyAnnuity;
    }

    public double calculateInterest(double principal, float interestRate) {
        double interest =  interestRate *
                DAYS_OF_MONTH *
                principal / DAYS_OF_YEAR;

        if(log.isDebugEnabled()) {
            log.debug("Interest amount for principal {}, interest rate {}, days in month {}, days in year {} is : {}",
                    principal, interestRate, DAYS_OF_MONTH, DAYS_OF_YEAR, interest);
        }

        return interest;
    }
}
