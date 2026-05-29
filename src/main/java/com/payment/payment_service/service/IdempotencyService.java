package com.payment.payment_service.service;

import com.google.common.hash.Hashing;
import com.payment.payment_service.model.IdempotencyRecord;
import com.payment.payment_service.model.PaymentRequest;
import com.payment.payment_service.repository.IdempotencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.nio.charset.StandardCharsets;

@Service
public class IdempotencyService {
    @Autowired
    private IdempotencyRepository idempotencyRepository;

    //Generate Idempotency Key
    public String generateIdempotencyKey(PaymentRequest request) {
        String str =  request.getOrderId() + "_" + request.getUserId() + "_" + request.getMerchantId() + "_" + request.getAmount() + "_" + request.getPaymentMethod();
        return Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString();
    }

    public void save(String key, String transactionId) {
        IdempotencyRecord record = new IdempotencyRecord(key, transactionId);

        idempotencyRepository.save(record);
    }

    // Check Existing Key
    public String getTransactionId(String key) {

        Optional<IdempotencyRecord> optional =
                idempotencyRepository.findById(key);

        return optional
                .map(IdempotencyRecord::getTransactionId)
                .orElse(null);
    }
}
