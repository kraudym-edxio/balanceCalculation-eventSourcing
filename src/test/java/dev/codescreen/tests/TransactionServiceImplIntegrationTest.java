package dev.codescreen.tests;

import dev.codescreen.model.Amount;
import dev.codescreen.model.Event;
import dev.codescreen.model.EventStore;
import dev.codescreen.service.TransactionServiceImpl;
import dev.codescreen.util.TransactionEnums;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceImplIntegrationTest {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private EventStore eventStore;

    @Test
    void whenProcessAuthorization_thenEventStored() {
        eventStore.getAllEvents().clear();

        String userId = "user1";
        String messageId = "auth1";
        Amount transactionAmount = new Amount("100.00", "USD", TransactionEnums.DebitCredit.DEBIT);

        transactionService.processAuthorization(userId, messageId, transactionAmount);
        assertEquals(1, eventStore.getAllEvents().size());

        Event storedEvent = eventStore.getAllEvents().get(0);
        assertEquals(userId, storedEvent.getUserId());
        assertEquals(messageId, storedEvent.getMessageId());
        assertEquals(transactionAmount.getAmount(), storedEvent.getTransactionAmount().getAmount());
    }

    @Test
    void whenProcessLoad_thenEventStored() {
        eventStore.getAllEvents().clear();

        String userId = "user2";
        String messageId = "load1";
        Amount loadAmount = new Amount("150.00", "USD", TransactionEnums.DebitCredit.CREDIT);

        transactionService.processLoad(userId, messageId, loadAmount);
        assertEquals(1, eventStore.getAllEvents().size());

        Event storedEvent = eventStore.getAllEvents().get(0);
        assertEquals(userId, storedEvent.getUserId());
        assertEquals(messageId, storedEvent.getMessageId());
        assertEquals(loadAmount.getAmount(), storedEvent.getTransactionAmount().getAmount());
    }

    @Test
    void whenGetBalanceForUnknownUser_thenInitialBalanceIsReturned() {
        String userId = "unknownUser";

        Amount initialBalance = transactionService.getCurrentBalance(userId);

        assertNotNull(initialBalance);
        assertEquals("500.00", initialBalance.getAmount());
        assertEquals("USD", initialBalance.getCurrency());
        assertEquals(TransactionEnums.DebitCredit.CREDIT, initialBalance.getDebitOrCredit());
    }

    @Test
    void whenCalculateUpdatedBalanceWithInvalidTransactionType_thenThrowsException() {
        String userId = "user3";
        Amount transactionAmount = new Amount("300.00", "USD", null);

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.processAuthorization(userId, "msg3", transactionAmount);
        });
    }
}
