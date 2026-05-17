package com.payment.payment_service.model;

public class FraudResponse {
    public String transactionId;
    private double riskScore;
    private boolean fraud;

    public FraudResponse() {
    }

    public FraudResponse(String transactionId, double riskScore, boolean fraud) {
        this.transactionId = transactionId;
        this.riskScore = riskScore;
        this.fraud = fraud;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public boolean isFraud() {
        return fraud;
    }

    public void setFraud(boolean fraud) {
        this.fraud = fraud;
    }
}
