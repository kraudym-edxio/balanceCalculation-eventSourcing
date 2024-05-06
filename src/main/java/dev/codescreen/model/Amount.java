package dev.codescreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.codescreen.util.TransactionEnums;

public class Amount {
    private String amount;
    private String currency;
    private TransactionEnums.DebitCredit debitOrCredit;

    public Amount() {
    }

    public Amount(String amount, String currency, TransactionEnums.DebitCredit debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return String.format("%.2f", Double.parseDouble(amount));
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("debitOrCredit")
    public TransactionEnums.DebitCredit getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(TransactionEnums.DebitCredit debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }
}
