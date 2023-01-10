package choice.university.ivan.soapapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ServiceStatusTest {
    @Test
    void testConstructor() {
        ServiceStatus serviceStatus = new ServiceStatus(404, "NOT FOUND", "Hotel with id: 1 not found");
        assertEquals(serviceStatus.getStatusCode(), 404);
        assertEquals(serviceStatus.getStatusName(), "NOT FOUND");
        assertEquals(serviceStatus.getMessage(), "Hotel with id: 1 not found");
    }

    @Test
    void testSettersAndGetters() {
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        serviceStatus.setStatusName("NOT FOUND");
        serviceStatus.setMessage("Hotel with id: 1 not found");
        assertEquals(serviceStatus.getStatusCode(), 404);
        assertEquals(serviceStatus.getStatusName(), "NOT FOUND");
        assertEquals(serviceStatus.getMessage(), "Hotel with id: 1 not found");
    }

    @Test
    public void testHashCode() {
        ServiceStatus serviceStatus = new ServiceStatus(404, "NOT FOUND", "Hotel with id: 1 not found");
        assertTrue(serviceStatus.hashCode() == serviceStatus.hashCode());
        assertTrue(new ServiceStatus().hashCode() == new ServiceStatus().hashCode());
    }

    @Test
    public void testEquals() {
        ServiceStatus serviceStatus = new ServiceStatus(404, "NOT FOUND", "Hotel with id: 1 not found");
        assertTrue(serviceStatus.equals(serviceStatus));
        assertFalse(serviceStatus.equals(null));
        assertFalse(serviceStatus.equals(new Object()));
        assertFalse(serviceStatus.equals(new ServiceStatus(400, "BAD REQUEST", "Name is requiered")));
        assertFalse(new ServiceStatus(404, null, "Hotel with id: 1 not found")
                .equals(serviceStatus));
        assertTrue(new ServiceStatus(404, null, "Hotel with id: 1 not found")
                .equals(new ServiceStatus(404, null, "Hotel with id: 1 not found")));
        assertFalse(new ServiceStatus(404, "NOT FOUND", "Hotel with id: 1 not found")
                .equals(new ServiceStatus(404, "BAD REQUEST", "Hotel with id: 1 not found")));
        assertFalse(new ServiceStatus(404, null, "Hotel with id: 1 not found")
                .equals(serviceStatus));
        assertTrue(new ServiceStatus(404, "NOT FOUND", null)
                .equals(new ServiceStatus(404, "NOT FOUND", null)));
        assertFalse(new ServiceStatus(404, "NOT FOUND", "Hotel with id: 1 not found")
                .equals(new ServiceStatus(404, "NOT FOUND", "Hotel with id: 2 not found")));
        assertFalse(new ServiceStatus(404, "NOT FOUND", null)
                .equals(serviceStatus));
    }
}
