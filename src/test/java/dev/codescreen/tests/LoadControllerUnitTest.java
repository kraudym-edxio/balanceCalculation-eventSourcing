package dev.codescreen.tests;

import dev.codescreen.controller.LoadController;
import dev.codescreen.model.*;
import dev.codescreen.model.Error;
import dev.codescreen.service.TransactionService;
import dev.codescreen.util.TransactionEnums;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerErrorException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadControllerUnitTest {

    @InjectMocks
    LoadController loadController;

    @Mock
    TransactionService transactionService;

    @BeforeEach
    void setup() {
        this.loadController = new LoadController(transactionService);
    }

    @Test
    public void testLoadTransaction() {
        String userId = "456";
        String messageId = "X67JU";
        Amount transactionAmount = new Amount("60.00", "USD", TransactionEnums.DebitCredit.CREDIT);
        Amount updatedBalance = new Amount("560.00", "USD", TransactionEnums.DebitCredit.CREDIT);

        LoadRequest loadRequest = new LoadRequest(userId, messageId, transactionAmount);
        when(transactionService.processLoad(userId, messageId, transactionAmount)).thenReturn(updatedBalance);

        ResponseEntity<LoadResponse> responseEntity = loadController.loadTransaction(messageId, loadRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat((Objects.requireNonNull(responseEntity.getBody())).getUserId()).isEqualTo(userId);
        assertThat((responseEntity.getBody()).getMessageId()).isEqualTo(messageId);
        assertThat((responseEntity.getBody()).getBalance()).isEqualTo(updatedBalance);
    }

    @Test
    void testHandleServerError() {
        ServerErrorException ex = new ServerErrorException("Internal Server Error", null);
        ResponseEntity<Error> responseEntity = loadController.handleServerError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        if (responseEntity.getBody() != null) {
            assertEquals("500 INTERNAL_SERVER_ERROR \"Internal Server Error\"", responseEntity.getBody().getMessage());
        } else {
            fail("Response entity body is null");
        }
    }
}
