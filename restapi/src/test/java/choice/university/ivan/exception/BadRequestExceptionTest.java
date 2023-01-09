package choice.university.ivan.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BadRequestExceptionTest {
    @Test
    public void testContructor() {
        BadRequestException ex = new BadRequestException("Test");
        assertEquals(ex.getMessage(), "Test");
    }
}
