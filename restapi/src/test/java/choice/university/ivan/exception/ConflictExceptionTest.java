package choice.university.ivan.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConflictExceptionTest {
    @Test
    public void testContructor() {
        ConflictException ex = new ConflictException("Test");
        assertEquals(ex.getMessage(), "Test");
    }
}
