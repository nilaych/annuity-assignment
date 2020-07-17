package com.lendico.assignment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lendico.assignment.util.AmountSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
public class BorrowerPayment {

    @JsonSerialize(using = AmountSerializer.class)
    private double borrowerPaymentAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    private Date date;

    @JsonSerialize(using = AmountSerializer.class)
    private double initialOutstandingPrincipal;

    @JsonSerialize(using = AmountSerializer.class)
    private double interest;

    @JsonSerialize(using = AmountSerializer.class)
    private double principal;

    @JsonSerialize(using = AmountSerializer.class)
    private double remainingOutstandingPrincipal;
}
