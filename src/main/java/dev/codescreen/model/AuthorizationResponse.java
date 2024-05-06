package dev.codescreen.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.codescreen.util.TransactionEnums;

@JsonPropertyOrder({ "userId", "messageId", "responseCode", "balance" })
public class AuthorizationResponse {
    private String userId;
    private String messageId;
    private TransactionEnums.ResponseCode responseCode;
    private Amount balance;

    public AuthorizationResponse() {
    }

    public AuthorizationResponse(String userId, String messageId, TransactionEnums.ResponseCode responseCode, Amount balance) {
        this.userId = userId;
        this.messageId = messageId;
        this.responseCode = responseCode;
        this.balance = balance;
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

    public TransactionEnums.ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(TransactionEnums.ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
}
