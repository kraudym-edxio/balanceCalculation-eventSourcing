package dev.codescreen.controller;

import dev.codescreen.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;
import dev.codescreen.model.Amount;
import dev.codescreen.service.TransactionService;
import org.springframework.web.server.ServerErrorException;

@RestController
@RequestMapping("/load")
public class LoadController {

    private final TransactionService transactionService;

    public LoadController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/{messageId}")
    public @ResponseBody ResponseEntity<LoadResponse> loadTransaction(@PathVariable String messageId, @RequestBody LoadRequest request) {
        String userId = request.getUserId();
        Amount transactionAmount = request.getTransactionAmount();

        // Delegate load processing to the service
        Amount updatedBalance = transactionService.processLoad(userId, messageId, transactionAmount);

        LoadResponse response = new LoadResponse(userId, messageId, updatedBalance);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Error> handleServerError(ServerErrorException ex) {
        Error error = new Error(ex.getMessage(), ex.getStatusCode().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
