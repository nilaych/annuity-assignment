package com.lendico.assignment.service.helper;

import com.lendico.assignment.model.BorrowerPayment;
import com.lendico.assignment.util.AnnuityCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BorrowerPaymentCalculatorTest {

    @InjectMocks
    private BorrowerPaymentCalculator borrowerPaymentcalculator;

    @Mock
    private AnnuityCalculator annuityCalculator;

    @Captor
    private ArgumentCaptor<Double> principalArgumentCaptor;
    @Captor
    private ArgumentCaptor<Float> interestRateArgumentCaptor;
    @Captor
    private ArgumentCaptor<Integer> durationArgumentCaptor;

    private static final double PRINCIPAL_AMOUNT = 5000.0d;
    private static final float NOMINAL_RATE = 5.0f;
    private static final int DURATION = 24;
    private static final double ANNUITY_AMOUNT = 220.04d;
    private static final double MONTHLY_INTEREST = 22.34d;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(annuityCalculator.calculateAnnuity(anyDouble(), anyFloat(), anyInt()))
                .thenReturn(ANNUITY_AMOUNT);
        when(annuityCalculator.calculateInterest(anyDouble(), anyFloat()))
                .thenReturn(MONTHLY_INTEREST);
    }

    @Test
    public void testPaymentDetails() {
        // Given
        Date startDate = new Date();

        // When
        List<BorrowerPayment> paymentDetails = borrowerPaymentcalculator.calculate(PRINCIPAL_AMOUNT, NOMINAL_RATE, DURATION, startDate);

        // Then
        assertEquals(DURATION, paymentDetails.size(), "Incorrect number of payments");

        for(BorrowerPayment payment : paymentDetails) {
            // Next payment date should be one month after the previous date
            assertEquals(payment.getDate(), startDate, "Incorrect payment date");
            startDate = calculateNextDate(startDate, 1);
        }

        verify(annuityCalculator, times(1)).calculateAnnuity(
                principalArgumentCaptor.capture(),
                interestRateArgumentCaptor.capture(),
                durationArgumentCaptor.capture());

        assertEquals(PRINCIPAL_AMOUNT, principalArgumentCaptor.getValue());
        assertEquals(NOMINAL_RATE/100/12, interestRateArgumentCaptor.getValue());
        assertEquals(DURATION, durationArgumentCaptor.getValue());

        verify(annuityCalculator, times(DURATION)).calculateInterest(
                principalArgumentCaptor.capture(),
                interestRateArgumentCaptor.capture());
    }

    private Date calculateNextDate(Date startDate, int afterMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, afterMonth);
        return calendar.getTime();
    }
}
