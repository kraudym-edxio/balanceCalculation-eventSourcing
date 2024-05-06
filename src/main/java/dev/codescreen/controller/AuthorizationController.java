package dev.codescreen.controller;

import dev.codescreen.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.AuthorizationResponse;
import dev.codescreen.model.Amount;
import dev.codescreen.service.TransactionService;
import org.springframework.web.server.ServerErrorException;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    private final TransactionService transactionService;

    public AuthorizationController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/{messageId}")
    public @ResponseBody ResponseEntity<AuthorizationResponse> authorizeTransaction(@PathVariable String messageId, @RequestBody AuthorizationRequest request) {
        String userId = request.getUserId();
        Amount transactionAmount = request.getTransactionAmount();

        // Delegate authorization processing to the service
        AuthorizationResponse response = transactionService.processAuthorization(userId, messageId, transactionAmount);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Error> handleServerError(ServerErrorException ex) {
        Error error = new Error(ex.getMessage(), ex.getStatusCode().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
