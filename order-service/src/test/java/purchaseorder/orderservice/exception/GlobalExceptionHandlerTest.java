package purchaseorder.orderservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.orderservice.dto.ApiResponse;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstraintViolationExceptionHandler() {
        ConstraintViolationException exception = new ConstraintViolationException("Validation error", null);

        ResponseEntity<ApiResponse<String>> response = globalExceptionHandler.constraintViolationException(exception);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Failed", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Validation error", response.getBody().getMessage());
    }

    @Test
    void testResponseStatusExceptionHandler() {
        ResponseStatusException exception = new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "User not found");

        ResponseEntity<ApiResponse<String>> response = globalExceptionHandler.responseStatusException(exception);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Failed", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("404 NOT_FOUND \"User not found\"", response.getBody().getMessage());
    }

    @Test
    void testGeneralExceptionHandler() {
        Exception exception = new RuntimeException("Unexpected error");

        ResponseEntity<ApiResponse<String>> response = globalExceptionHandler.exception(exception);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Failed", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }
}
