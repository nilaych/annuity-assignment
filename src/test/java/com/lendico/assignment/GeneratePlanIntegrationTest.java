package com.lendico.assignment;

import com.lendico.assignment.model.BorrowerPayment;
import com.lendico.assignment.model.BorrowerPlanRequest;
import com.lendico.assignment.model.BorrowerPlanResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeneratePlanIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSuccess() {
        ResponseEntity<BorrowerPlanResponse> response = restTemplate.postForEntity("/generate-plan", createRequestPayload(), BorrowerPlanResponse.class);
        List<BorrowerPayment> borrowerPayments = response.getBody().getBorrowerPayments();
        assertEquals("Service failed", response.getStatusCode(), HttpStatus.OK);
        assertEquals("Incorrect number of payments", borrowerPayments.size(), 12);
        assertEquals("Remaining principal after last payment should be zero",
                borrowerPayments.get(borrowerPayments.size() - 1).getRemainingOutstandingPrincipal(), 0.0d);
    }

    private BorrowerPlanRequest createRequestPayload() {
        BorrowerPlanRequest request = new BorrowerPlanRequest();
        request.setLoanAmount(5000.00d);
        request.setNominalRate(5.0f);
        request.setDuration(12);
        request.setStartDate(new Date());
        return request;
    }
}
