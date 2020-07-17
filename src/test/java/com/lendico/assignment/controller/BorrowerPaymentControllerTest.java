package com.lendico.assignment.controller;

import com.lendico.assignment.model.BorrowerPayment;
import com.lendico.assignment.model.BorrowerPlanRequest;
import com.lendico.assignment.model.BorrowerPlanResponse;
import com.lendico.assignment.service.BorrowerPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BorrowerPaymentControllerTest {

    @InjectMocks
    private BorrowerPaymentController controller;

    @Mock
    private BorrowerPaymentService borrowerPaymentService;
    @Captor
    private ArgumentCaptor<BorrowerPlanRequest> requestArgumentCaptor;
    private List<BorrowerPayment> successResponse = createResponse();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReturnPayments() {
        // Given
        BorrowerPlanRequest request = createRequestPayload();
        when(borrowerPaymentService.generatePlan(eq(request))).thenReturn(successResponse);

        // When
        BorrowerPlanResponse response = controller.generatePlan(request);

        // Then
        assertEquals(response.getBorrowerPayments(), successResponse);
        verify(borrowerPaymentService, times(1)).generatePlan(requestArgumentCaptor.capture());
        assertEquals(request, requestArgumentCaptor.getValue());
    }

    private BorrowerPlanRequest createRequestPayload() {
        BorrowerPlanRequest request = new BorrowerPlanRequest();
        request.setLoanAmount(5000.00d);
        request.setNominalRate(5.0f);
        request.setDuration(12);
        request.setStartDate(new Date());
        return request;
    }

    private List<BorrowerPayment> createResponse() {
        return Arrays.asList(
                BorrowerPayment.builder()
                .borrowerPaymentAmount(200)
                .date(new Date())
                .initialOutstandingPrincipal(200)
                .interest(5)
                .principal(150)
                .remainingOutstandingPrincipal(50)
                .build()
        );
    }
}
