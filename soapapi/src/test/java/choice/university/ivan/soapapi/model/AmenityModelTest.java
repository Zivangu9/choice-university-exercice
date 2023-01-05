package choice.university.ivan.soapapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AmenityModelTest {
    @Test
    void testConstructor() {
        AmenityModel amenityModel = new AmenityModel(1, "Name");
        assertEquals(amenityModel.getId(), 1);
        assertEquals(amenityModel.getName(), "Name");
    }

    @Test
    void testSettersAndGetters() {
        AmenityModel amenityModel = new AmenityModel();
        amenityModel.setId(1);
        amenityModel.setName("Name");
        assertEquals(amenityModel.getId(), 1);
        assertEquals(amenityModel.getName(), "Name");
    }

}
