package com.lendico.assignment.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BorrowerPlanResponse {
    private List<BorrowerPayment> borrowerPayments;

    public BorrowerPlanResponse() {
    }

    public BorrowerPlanResponse(List<BorrowerPayment> borrowerPayments) {
        this.borrowerPayments = borrowerPayments;
    }
}
