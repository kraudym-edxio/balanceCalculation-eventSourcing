package dev.codescreen.service;

import dev.codescreen.model.Amount;
import dev.codescreen.model.AuthorizationResponse;

public interface TransactionService {
    Amount getCurrentBalance(String userId);

    boolean isSufficientFunds(Amount currentBalance, Amount transactionAmount);

    AuthorizationResponse processAuthorization(String userId, String messageId, Amount transactionAmount);

    Amount processLoad(String userId, String messageId, Amount transactionAmount);
}
