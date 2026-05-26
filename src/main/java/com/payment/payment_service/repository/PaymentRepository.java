package com.payment.payment_service.repository;

import com.payment.payment_service.model.PaymentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentResponse, String> {
}