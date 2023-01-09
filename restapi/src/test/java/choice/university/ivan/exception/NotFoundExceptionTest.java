package choice.university.ivan.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NotFoundExceptionTest {
    @Test
    public void testContructor() {
        NotFoundException ex = new NotFoundException("Test");
        assertEquals(ex.getMessage(), "Test");
    }
}
