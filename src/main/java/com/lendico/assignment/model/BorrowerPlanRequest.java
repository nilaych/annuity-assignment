package com.lendico.assignment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
public class BorrowerPlanRequest {

    /**
     * Principal amount
     */
    private double loanAmount;

    /**
     * Annual interest rate
     */
    private float nominalRate;

    /**
     * Number of installments in months
     */
    private int duration;

    /**
     * Date of Disbursement/Payout
     */
    private Date startDate;
}
