package ar.edu.utn.frc.tup.lc.iv.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    void testConstructorAndGetMessage() {

        String expectedMessage = "Test error message";
        ErrorResponse errorResponse = new ErrorResponse(expectedMessage);
        assertEquals(expectedMessage, errorResponse.getMessage(),
                "The message should match the value provided in the constructor");
    }

    @Test
    void testSetMessage() {
        // Arrange
        String initialMessage = "Initial message";
        String updatedMessage = "Updated message";
        ErrorResponse errorResponse = new ErrorResponse(initialMessage);

        errorResponse.setMessage(updatedMessage);
        assertEquals(updatedMessage, errorResponse.getMessage(),
                "The message should match the updated value");
    }
}
