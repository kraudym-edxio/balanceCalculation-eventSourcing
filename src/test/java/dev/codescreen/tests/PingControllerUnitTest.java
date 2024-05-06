package dev.codescreen.tests;

import dev.codescreen.model.Error;
import dev.codescreen.controller.PingController;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerErrorException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PingControllerUnitTest {

    private final PingController pingController = new PingController();

    @Test
    void testPing() {
        ResponseEntity<PingController.PingResponse> responseEntity = pingController.ping();
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getServerTime());
    }

    @Test
    void testHandleServerError() {
        ServerErrorException ex = new ServerErrorException("Internal Server Error", null);
        ResponseEntity<Error> responseEntity = pingController.handleServerError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        if (responseEntity.getBody() != null) {
            assertEquals("500 INTERNAL_SERVER_ERROR \"Internal Server Error\"", responseEntity.getBody().getMessage());
        } else {
            fail("Response entity body is null");
        }
    }
}