package com.lendico.assignment.service.helper;

import com.lendico.assignment.model.BorrowerPayment;
import com.lendico.assignment.util.AnnuityCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class BorrowerPaymentCalculator {

    @Autowired
    private AnnuityCalculator annuityCalculator;

    public List<BorrowerPayment> calculate(double loanAmount,
            float nominalRate,
            int duration,
            Date startDate) {
        float interestRate = nominalRate / 100;
        float monthlyInterestRate = interestRate / 12;
        double monthlyAnnuity = annuityCalculator.calculateAnnuity(loanAmount, monthlyInterestRate, duration);

        List<BorrowerPayment> payments = new ArrayList<>(duration);
        calculate(loanAmount, monthlyAnnuity, interestRate, startDate, duration, payments);
        return payments;
    }

    private void calculate(double principal,
                           double annuity,
                           float interestRate,
                           Date date,
                           int duration,
                           List<BorrowerPayment> payments) {

        double monthlyInterest = annuityCalculator.calculateInterest(principal, interestRate);
        double monthlyPrincipal = annuity - monthlyInterest;
        double remainingPrincipal = principal - monthlyPrincipal;

        BorrowerPayment payment = BorrowerPayment.builder()
                .borrowerPaymentAmount(annuity)
                .date(date)
                .initialOutstandingPrincipal(principal)
                .interest(monthlyInterest)
                .principal(monthlyPrincipal)
                .remainingOutstandingPrincipal(remainingPrincipal)
                .build();

        log.info("Payment for month[{}] is {}", (payments.size() + 1), payment);

        payments.add(payment);

        if (payments.size() < duration) {
            calculate(remainingPrincipal, annuity, interestRate, nextDate(date), duration, payments);
        }
    }

    /**
     * Calculates the next date of payment
     * @param date The start date
     * @return Date of next month
     */
    private Date nextDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
}
