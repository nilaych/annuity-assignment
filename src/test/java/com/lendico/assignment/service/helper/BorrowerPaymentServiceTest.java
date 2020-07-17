package com.lendico.assignment.service.helper;

import com.lendico.assignment.model.BorrowerPayment;
import com.lendico.assignment.model.BorrowerPlanRequest;
import com.lendico.assignment.service.BorrowerPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BorrowerPaymentServiceTest {

    @InjectMocks
    private BorrowerPaymentService borrowerPaymentService;
    @Mock
    private BorrowerPaymentCalculator borrowerPaymentCalculator;
    @Captor
    private ArgumentCaptor<Double> principalArgumentCaptor;
    @Captor
    private ArgumentCaptor<Float> interestRateArgumentCaptor;
    @Captor
    private ArgumentCaptor<Integer> durationArgumentCaptor;
    @Captor
    private ArgumentCaptor<Date> startDateArgumentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPayments() {
        BorrowerPlanRequest request = createRequest();
        List<BorrowerPayment> mockPayments = Arrays.asList(Mockito.mock(BorrowerPayment.class));
        // Given
        when(borrowerPaymentCalculator.calculate(eq(
                request.getLoanAmount()),
                eq(request.getNominalRate()),
                eq(request.getDuration()),
                eq(request.getStartDate())))
                .thenReturn(mockPayments);

        // When
        List<BorrowerPayment> borrowerPayments = borrowerPaymentService.generatePlan(request);
        assertEquals(mockPayments, borrowerPayments);
        verify(borrowerPaymentCalculator, times(1))
                .calculate(
                        principalArgumentCaptor.capture(),
                        interestRateArgumentCaptor.capture(),
                        durationArgumentCaptor.capture(),
                        startDateArgumentCaptor.capture());

        assertEquals(request.getLoanAmount(), principalArgumentCaptor.getValue());
        assertEquals(request.getNominalRate(), interestRateArgumentCaptor.getValue());
        assertEquals(request.getDuration(), durationArgumentCaptor.getValue());
        assertEquals(request.getStartDate(), startDateArgumentCaptor.getValue());
    }

    private BorrowerPlanRequest createRequest() {
        BorrowerPlanRequest request = new BorrowerPlanRequest();
        request.setLoanAmount(5000.00d);
        request.setNominalRate(5.0f);
        request.setDuration(12);
        request.setStartDate(new Date());
        return request;
    }
}
