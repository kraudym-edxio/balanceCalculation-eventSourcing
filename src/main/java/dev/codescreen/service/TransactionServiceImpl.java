package dev.codescreen.service;

import org.springframework.stereotype.Service;
import dev.codescreen.model.Amount;
import dev.codescreen.model.AuthorizationResponse;
import dev.codescreen.model.Event;
import dev.codescreen.model.EventStore;
import dev.codescreen.util.TransactionEnums;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Map<String, Amount> balanceStore = new HashMap<>();
	private final EventStore eventStore;

    public TransactionServiceImpl(EventStore eventStore) {
        this.eventStore = eventStore;
    }
	
    @Override
    public Amount getCurrentBalance(String userId) {
        if (!this.balanceStore.containsKey(userId)) {
            this.balanceStore.put(userId, new Amount("500.00", "USD", TransactionEnums.DebitCredit.CREDIT));
        }
        return this.balanceStore.get(userId);
    }

    @Override
    public boolean isSufficientFunds(Amount currentBalance, Amount transactionAmount) {
        double balance = Double.parseDouble(currentBalance.getAmount());
        double amount = Double.parseDouble(transactionAmount.getAmount());
        return balance >= amount;
    }

    @Override
    public AuthorizationResponse processAuthorization(String userId, String messageId, Amount transactionAmount) {
        Amount currentBalance = getCurrentBalance(userId);
        TransactionEnums.ResponseCode responseCode;
        Amount updatedBalance;

        if (isSufficientFunds(currentBalance, transactionAmount)) {
        	updatedBalance = calculateUpdatedBalance(currentBalance, transactionAmount);
        	responseCode = TransactionEnums.ResponseCode.APPROVED;
        } else {
        	updatedBalance = currentBalance;
        	responseCode = TransactionEnums.ResponseCode.DECLINED;
        }

        this.balanceStore.put(userId, updatedBalance);
        AuthorizationResponse response = new AuthorizationResponse(userId, messageId, responseCode, updatedBalance);
        eventStore.storeEvent(new Event(userId, messageId, transactionAmount, updatedBalance, responseCode));
        return response;
    }

    @Override
    public Amount processLoad(String userId, String messageId, Amount transactionAmount) {
        Amount currentBalance = getCurrentBalance(userId);
        Amount updatedBalance = calculateUpdatedBalance(currentBalance, transactionAmount);
        this.balanceStore.put(userId, updatedBalance);
        eventStore.storeEvent(new Event(userId, messageId, transactionAmount, updatedBalance, TransactionEnums.ResponseCode.APPROVED));
        return new Amount(updatedBalance.getAmount(), currentBalance.getCurrency(), TransactionEnums.DebitCredit.CREDIT);
    }


    private Amount calculateUpdatedBalance(Amount currentBalance, Amount transactionAmount) {
    	double balance = Double.parseDouble(currentBalance.getAmount());
        double amount = Double.parseDouble(transactionAmount.getAmount());
        TransactionEnums.DebitCredit debitOrCredit = transactionAmount.getDebitOrCredit();

        double updatedBalance;

        if (TransactionEnums.DebitCredit.DEBIT.equals(debitOrCredit)) { // Subtracting for debit transactions
            updatedBalance = balance - amount; 
        } else if (TransactionEnums.DebitCredit.CREDIT.equals(debitOrCredit)) { // Adding for credit transactions
            updatedBalance = balance + amount; 
        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + debitOrCredit);
        }

        return new Amount(String.valueOf(updatedBalance), currentBalance.getCurrency(), TransactionEnums.DebitCredit.CREDIT);
    }
}
