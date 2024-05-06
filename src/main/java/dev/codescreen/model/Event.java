package dev.codescreen.model;

import java.time.LocalDateTime;

import dev.codescreen.util.TransactionEnums;

public class Event {
    private String userId;
    private String messageId;
    private Amount transactionAmount;
    private Amount updatedBalance;
    private TransactionEnums.ResponseCode responseCode;
    private LocalDateTime timestamp;

    public Event() {
        this.timestamp = LocalDateTime.now();
    }

    public Event(String userId, String messageId, Amount transactionAmount, Amount updatedBalance, TransactionEnums.ResponseCode responseCode) {
        this.userId = userId;
        this.messageId = messageId;
        this.transactionAmount = transactionAmount;
        this.updatedBalance = updatedBalance;
        this.responseCode = responseCode;
        this.timestamp = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Amount getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(Amount updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

    public TransactionEnums.ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(TransactionEnums.ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
