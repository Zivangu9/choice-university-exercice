package choice.university.ivan.soapapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class HotelModelTest {
    @Test
    void testConstructor() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 8.7);
        assertEquals(hotelModel.getId(), 1);
        assertEquals(hotelModel.getName(), "Hotel Name");
        assertEquals(hotelModel.getAddress(), "Hotel Address");
        assertEquals(hotelModel.getRating(), 8.7);
    }

    @Test
    void testSettersAndGetters() {
        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(1);
        hotelModel.setName("Hotel Name");
        hotelModel.setAddress("Hotel Address");
        hotelModel.setRating(7.4);
        assertEquals(hotelModel.getId(), 1);
        assertEquals(hotelModel.getName(), "Hotel Name");
        assertEquals(hotelModel.getAddress(), "Hotel Address");
        assertEquals(hotelModel.getRating(), 7.4);
    }

    @Test
    public void testHashCode() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 8.7);
        assertTrue(hotelModel.hashCode() == hotelModel.hashCode());
        assertTrue(new HotelModel().hashCode() == new HotelModel().hashCode());
    }

    @Test
    public void testEquals() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 8.5);
        HotelModel hotelModel2 = new HotelModel(1, "Hotel Name", "Hotel Address", 8.6);
        assertTrue(hotelModel.equals(hotelModel));
        assertFalse(hotelModel.equals(null));
        assertFalse(hotelModel.equals(new Object()));
        assertFalse(hotelModel.equals(new HotelModel(2, "Name", "Address", 4.8)));
        assertFalse(new HotelModel(null, "Name", "Address", 4.8).equals(new HotelModel(1, null, "Address", 4.8)));
        assertFalse(new HotelModel(null, "Name", "Address", 4.8).equals(new HotelModel(null, null, "Address", 4.8)));
        assertTrue(new HotelModel(1, null, "Address", 4.8).equals(new HotelModel(1, null, "Address", 4.8)));
        assertFalse(new HotelModel(1, null, "Address", 4.8).equals(new HotelModel(1, "Name", "Address", 4.8)));
        assertFalse(hotelModel.equals(new HotelModel(1, "Name", "Hotel Address", 4.8)));
        assertTrue(new HotelModel(1, "Hotel Name", null, 4.8).equals(new HotelModel(1, "Hotel Name", null, 4.8)));
        assertFalse(new HotelModel(1, "Hotel Name", null, 4.8)
                .equals(new HotelModel(1, "Hotel Name", "Hotel Address", 4.8)));
        assertFalse(hotelModel.equals(new HotelModel(1, "Hotel Name", "Address", 4.8)));
        assertFalse(hotelModel.equals(hotelModel2));
        hotelModel2.setRating(8.5);
        assertTrue(hotelModel.equals(hotelModel2));
        List<AmenityModel> amenities = hotelModel.getAmenities();
        amenities.add(new AmenityModel());
        assertFalse(hotelModel.equals(hotelModel2));
    }
}
