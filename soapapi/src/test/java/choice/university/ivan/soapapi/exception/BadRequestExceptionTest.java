package choice.university.ivan.soapapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import choice.university.ivan.schemas.ServiceStatus;

public class BadRequestExceptionTest {
    @Test
    void testGetServiceStatus() {
        BadRequestException ex = new BadRequestException("Test");
        assertEquals(ex.getMessage(), "Test");
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(400);
        serviceStatus.setStatusName("BAD REQUEST");
        serviceStatus.setMessage("Test");
        assertEquals(ex.getMessage(), "Test");
        assertEquals(ex.getServiceStatus(), serviceStatus);
    }
}
