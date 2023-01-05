package choice.university.ivan.soapapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testHashCode() {
        AmenityModel amenityModel = new AmenityModel(1, "Amenity Name");
        assertTrue(amenityModel.hashCode() == amenityModel.hashCode());
        assertFalse(amenityModel.hashCode() == new AmenityModel().hashCode());
    }

    @Test
    public void testEquals() {
        AmenityModel amenityModel = new AmenityModel(1, "Amenity Name");
        assertTrue(amenityModel.equals(amenityModel));
        assertFalse(amenityModel.equals(null));
        assertFalse(amenityModel.equals(new Object()));
        assertFalse(amenityModel.equals(new AmenityModel(2, "Amenity Name")));
        assertTrue(new AmenityModel(null, null).equals(new AmenityModel(null, null)));
        assertFalse(new AmenityModel(null, null).equals(new AmenityModel(1, null)));
        assertTrue(new AmenityModel(1, null).equals(new AmenityModel(1, null)));
        assertFalse(new AmenityModel(1, null).equals(amenityModel));
        assertFalse(amenityModel.equals(new AmenityModel(1, "Name")));
        assertTrue(amenityModel.equals(new AmenityModel(1, "Amenity Name")));
    }

}
