package com.payment.payment_service.model;

public class RecoverySuggestionResponse {
    private String transactionId;
    private String suggestion;
    private String action;

    public RecoverySuggestionResponse() {
    }

    public RecoverySuggestionResponse(String transactionId, String suggestion, String action) {
        this.transactionId = transactionId;
        this.suggestion = suggestion;
        this.action = action;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
