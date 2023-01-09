package choice.university.ivan.soapapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import choice.university.ivan.schemas.ServiceStatus;

public class NotFoundExceptionTest {
    @Test
    void testGetServiceStatus() {
        NotFoundException ex = new NotFoundException("Test");
        assertEquals(ex.getMessage(), "Test");
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        serviceStatus.setStatusName("NOT FOUND");
        serviceStatus.setMessage("Test");
        assertEquals(ex.getMessage(), "Test");
        assertEquals(ex.getServiceStatus(), serviceStatus);

    }
}
