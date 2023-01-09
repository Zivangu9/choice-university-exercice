package choice.university.ivan.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HotelModelTest {

    @Test
    public void testConstructor() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 4.8);
        assertEquals(hotelModel.getId(), 1);
        assertEquals(hotelModel.getName(), "Hotel Name");
        assertEquals(hotelModel.getAddress(), "Hotel Address");
        assertEquals(hotelModel.getRating(), 4.8, 0.001);
    }

    @Test
    public void testSettersAndGetters() {
        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(1);
        hotelModel.setName("Hotel Name");
        hotelModel.setAddress("Hotel Address");
        hotelModel.setRating(7.9);
        assertEquals(hotelModel.getId(), 1);
        assertEquals(hotelModel.getName(), "Hotel Name");
        assertEquals(hotelModel.getAddress(), "Hotel Address");
        assertEquals(hotelModel.getRating(), 7.9, 0.001);
    }

    @Test
    public void testHashCode() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 8.7);
        hotelModel.setAmenities(new ArrayList<>());
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
        assertFalse(hotelModel.equals(new HotelModel(2, "Name", "Address", 8.7)));
        assertTrue(new HotelModel(1, null, "Address", 8.7).equals(new HotelModel(1, null, "Address", 8.7)));
        assertFalse(new HotelModel(1, null, "Address", 8.7).equals(new HotelModel(1, "Name", "Address", 8.7)));
        assertFalse(hotelModel.equals(new HotelModel(1, "Name", "Hotel Address", 8.7)));
        assertTrue(new HotelModel(1, "Hotel Name", null, 8.7).equals(new HotelModel(1, "Hotel Name", null, 8.7)));
        assertFalse(new HotelModel(1, "Hotel Name", null, 8.7)
                .equals(new HotelModel(1, "Hotel Name", "Hotel Address", 8.7)));
        assertFalse(hotelModel.equals(new HotelModel(1, "Hotel Name", "Address", 8.7)));
        assertFalse(hotelModel.equals(hotelModel2));
        hotelModel2.setRating(8.5);
        hotelModel.setAmenities(null);
        assertFalse(hotelModel.equals(hotelModel2));
        hotelModel2.setAmenities(null);
        assertTrue(hotelModel.equals(hotelModel2));
        List<AmenityModel> amenities = new ArrayList<>();
        amenities.add(new AmenityModel());
        hotelModel.setAmenities(amenities);
        assertFalse(hotelModel.equals(hotelModel2));
    }

}
