package com.payment.payment_service.model;

public class PaymentResponse {
    private String transactionId;
    private String status;
    private int retryCount;

    public PaymentResponse() {
    }

    public PaymentResponse(String transactionId, String status, int retryCount) {
        this.transactionId = transactionId;
        this.status = status;
        this.retryCount = retryCount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
};
