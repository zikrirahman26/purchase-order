package purchaseorder.orderservice.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationRequestTest {

    @InjectMocks
    private ValidationRequest validationRequest;

    @Mock
    private Validator validator;

    @Mock
    private ConstraintViolation<Object> constraintViolation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationRequest_NoViolations() {
        Object request = new Object();
        when(validator.validate(request)).thenReturn(Collections.emptySet());

        assertDoesNotThrow(() -> validationRequest.validationRequest(request));

        verify(validator, times(1)).validate(request);
    }

    @Test
    void testValidationRequest_WithViolations() {
        Object request = new Object();
        Set<ConstraintViolation<Object>> violations = Set.of(constraintViolation);

        when(validator.validate(request)).thenReturn(violations);

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> validationRequest.validationRequest(request));

        assertNotNull(exception.getConstraintViolations());
        assertEquals(1, exception.getConstraintViolations().size());

        verify(validator, times(1)).validate(request);
    }
}
