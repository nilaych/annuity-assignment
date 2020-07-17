package com.lendico.assignment.util;

public interface AnnuityCalculator {

    /**
     * Calculates the annuity amount to be paid monthly basis
     * @param loanAmount The principal amount
     * @param monthlyInterestRate The monthly interest rate
     * @param duration Number of installments in months
     * @return The monthly annuity amount
     */
    double calculateAnnuity(double loanAmount, float monthlyInterestRate, int duration);

    /**
     * Calculates the monthly interest to be paid
     * @param principal The principal amount on which interest to be calculated
     * @param interestRate The rate of interest (yearly)
     * @return The interest amount needs to be paid for a specific month
     */
    double calculateInterest(double principal, float interestRate);
}
