package com.payment.payment_service.service;

import com.payment.payment_service.model.PaymentResponse;
import com.payment.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse savePayment(PaymentResponse payment) {
        return paymentRepository.save(payment);
    }

    public PaymentResponse getByTransactionId(String transactionId) {
        return paymentRepository.findById(transactionId)
                .orElse(null);
    }
}
