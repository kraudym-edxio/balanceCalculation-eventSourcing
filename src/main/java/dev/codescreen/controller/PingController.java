package dev.codescreen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import dev.codescreen.model.Error;

import java.time.LocalDateTime;

@RestController
public class PingController {

    @GetMapping("/ping")
    public @ResponseBody ResponseEntity<PingResponse> ping() {
        PingResponse response = new PingResponse(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Error> handleServerError(ServerErrorException ex) {
        Error error = new Error(ex.getMessage(), ex.getStatusCode().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    public static class PingResponse {
        private final String serverTime;

        public PingResponse(String serverTime) {
            this.serverTime = serverTime;
        }
        
        // Serialization of PingResponse into JSON, uses getter methods to access properties
        public String getServerTime() {
            return serverTime;
        }
    }
}
