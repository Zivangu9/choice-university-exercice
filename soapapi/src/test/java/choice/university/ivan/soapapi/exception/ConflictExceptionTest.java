package choice.university.ivan.soapapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConflictExceptionTest {
    @Test
    void testGetServiceStatus() {
        ConflictException ex = new ConflictException("Test");
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        serviceStatus.setStatusName("CONFLICT");
        serviceStatus.setMessage("Test");
        assertEquals(ex.getMessage(), "Test");
        assertEquals(ex.getServiceStatus(), serviceStatus);
    }
}
