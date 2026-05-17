package com.payment.payment_service.controller;

import com.payment.payment_service.model.FraudResponse;
import com.payment.payment_service.model.PaymentRequest;
import com.payment.payment_service.model.PaymentResponse;
import com.payment.payment_service.model.RecoverySuggestionResponse;
import com.payment.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //Idempotency Storage
    private Map<String, String> idempotencyDatabase = new HashMap<>();

    //Payment API with Idempotency
    @PostMapping
    public PaymentResponse createPayment(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody PaymentRequest request) {
       // Check duplicate request
       if (idempotencyDatabase.containsKey(idempotencyKey)) {
            String existingTransactionId = idempotencyDatabase.get(idempotencyKey);
            return paymentService.getByTransactionId(existingTransactionId);
       }

       //Create new transaction
        String transactionId = UUID.randomUUID().toString();
        
        PaymentResponse response = new PaymentResponse(transactionId,"FAILED",0);

        paymentService.savePayment(response);
        
        //Store idempotency key
        idempotencyDatabase.put(idempotencyKey, transactionId);
        return response;
    }

    //Get payment status by transaction ID
    @GetMapping("/{transactionId}")
    public PaymentResponse getPaymentStatus(@PathVariable String transactionId) {
        return paymentService.getByTransactionId(transactionId);
    }
    
    //RETRY FAILED PAYMENT API
    @PostMapping("/retry/{transactionId}")
    public PaymentResponse retryPayment(@PathVariable String transactionId) {
        PaymentResponse payment = paymentService.getByTransactionId(transactionId);
        if(payment!=null && payment.getStatus().equals("FAILED")) {
            payment.setRetryCount(payment.getRetryCount()+1);
            payment.setStatus("RETRYING");
        }
        
        return payment;
    }
    
    //Fraud Detection API
    @GetMapping("/fraud-check/{transactionId}")
    public FraudResponse fraudCheck(@PathVariable String transactionId) {
        //Mocked response for demonstration
        PaymentResponse payment = paymentService.getByTransactionId(transactionId);
        if(payment == null) {
            return new FraudResponse(transactionId, 0.0, false);
        }
        double riskScore = Math.random(); // Mocked risk score
        boolean isFraud = riskScore > 0.7; // Mocked fraud decision
        return new FraudResponse(transactionId, riskScore, isFraud);
    }

    // Smart Recovery Suggestion API
    @GetMapping("/recovery-suggestion/{transactionId}")
    public RecoverySuggestionResponse recoverySuggestion(@PathVariable String transactionId) {
        //Mocked response for demonstration
        PaymentResponse payment = paymentService.getByTransactionId(transactionId);
        if (payment == null) {
            return new RecoverySuggestionResponse(transactionId, "Transaction not found", "N/A");
        }
        String suggestion;
        String action;

        if (payment.getRetryCount() == 0) {
            suggestion = "Temporary network issue detected.";
            action = "Please retry after 30 seconds.";
        } else if (payment.getRetryCount() < 3) {
            suggestion = "Retry with same payment method.";
            action = "Automatic retry recommended.";
        } else {
            suggestion = "Multiple retries failed.";
            action = "Consider using a different payment method or contact support.";
        }
        return new RecoverySuggestionResponse(transactionId, suggestion, action);
    }
}
