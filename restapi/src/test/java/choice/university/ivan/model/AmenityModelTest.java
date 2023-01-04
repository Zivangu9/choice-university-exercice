package choice.university.ivan.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AmenityModelTest {
    @Test
    public void testConstructor() {
        AmenityModel amenityModel = new AmenityModel(1, "Amenity Name");
        assertEquals(amenityModel.getId(), 1);
        assertEquals(amenityModel.getName(), "Amenity Name");
    }

    @Test
    public void testSettersAndGetters() {
        AmenityModel amenityModel = new AmenityModel();
        amenityModel.setId(1);
        amenityModel.setName("Amenity Name");
        assertEquals(amenityModel.getId(), 1);
        assertEquals(amenityModel.getName(), "Amenity Name");
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
        assertFalse(new AmenityModel(1, null).equals(amenityModel));
        assertFalse(amenityModel.equals(new AmenityModel(1, "Name")));
    }
}
