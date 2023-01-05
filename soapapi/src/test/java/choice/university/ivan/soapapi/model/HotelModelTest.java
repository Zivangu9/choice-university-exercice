package choice.university.ivan.soapapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
