package com.payment.payment_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "idempotency_records")
public class IdempotencyRecord {
    @Id
    private String idempotencyKey;

    private String transactionId;

}
