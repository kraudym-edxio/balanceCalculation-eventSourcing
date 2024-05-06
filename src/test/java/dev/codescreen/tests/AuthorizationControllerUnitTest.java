package dev.codescreen.tests;

import dev.codescreen.controller.AuthorizationController;
import dev.codescreen.model.Amount;
import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.AuthorizationResponse;
import dev.codescreen.model.Error;
import dev.codescreen.service.TransactionService;
import dev.codescreen.util.TransactionEnums;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerErrorException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerUnitTest {

    @Mock
    private TransactionService transactionService;

    private AuthorizationController authorizationController;

    @BeforeEach
    void setup() {
        this.authorizationController = new AuthorizationController(transactionService);
    }

    @Test
    void testAuthorizeTransaction() {
        Amount reqAmount = new Amount("50.00", "USD", TransactionEnums.DebitCredit.DEBIT);
        Amount resAmount = new Amount("450.00", "USD", TransactionEnums.DebitCredit.CREDIT);
        AuthorizationRequest request = new AuthorizationRequest("123", "HX256", reqAmount);
        AuthorizationResponse response = new AuthorizationResponse("123", "HX256", TransactionEnums.ResponseCode.APPROVED, resAmount);

        when(transactionService.processAuthorization(request.getUserId(), request.getMessageId(), request.getTransactionAmount())).thenReturn(response);

        ResponseEntity<AuthorizationResponse> expectedResponseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        ResponseEntity<AuthorizationResponse> actualResponseEntity = authorizationController.authorizeTransaction("HX256", request);

        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void testHandleServerError() {
        ServerErrorException ex = new ServerErrorException("Internal Server Error", null);
        ResponseEntity<Error> responseEntity = authorizationController.handleServerError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        if (responseEntity.getBody() != null) {
            assertEquals("500 INTERNAL_SERVER_ERROR \"Internal Server Error\"", responseEntity.getBody().getMessage());
        } else {
            fail("Response entity body is null");
        }
    }
}
