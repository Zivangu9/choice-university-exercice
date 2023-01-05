package choice.university.ivan.soapapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;

public class HotelMapperTest {
    @Test
    void testContructor() {
        HotelMapper hotelMapper = new HotelMapper();
        assertNotNull(hotelMapper);
    }

    @Test
    void testMapHotelsList() {
        List<HotelModel> hotelsModel = new ArrayList<>();
        hotelsModel.add(new HotelModel(1, "Hotel 1 Name", "Hotel 1 Address", 7.9));
        hotelsModel.add(new HotelModel(2, "Hotel 2 Name", "Hotel 2 Address", 9.4));
        hotelsModel.add(new HotelModel(3, "Hotel 3 Name", "Hotel 3 Address", 7.4));

        List<Hotel> hotelsExpected = new ArrayList<>();

        Hotel hotel1 = new Hotel();
        hotel1.setId(1);
        hotel1.setName("Hotel 1 Name");
        hotel1.setAddress("Hotel 1 Address");
        hotel1.setRating(7.9);
        hotelsExpected.add(hotel1);
        Hotel hotel2 = new Hotel();
        hotel2.setId(2);
        hotel2.setName("Hotel 2 Name");
        hotel2.setAddress("Hotel 2 Address");
        hotel2.setRating(9.4);
        hotelsExpected.add(hotel2);
        Hotel hotel3 = new Hotel();
        hotel3.setId(3);
        hotel3.setName("Hotel 3 Name");
        hotel3.setAddress("Hotel 3 Address");
        hotel3.setRating(7.4);
        hotelsExpected.add(hotel3);

        List<Hotel> hotelsMapped = new ArrayList<>();
        HotelMapper.mapHotelsList(hotelsModel, hotelsMapped);
        assertEquals(hotelsExpected, hotelsMapped);
    }

    @Test
    void testMapHotel() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 7.9);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Name");
        hotel.setAddress("Hotel Address");
        hotel.setRating(7.9);
        assertEquals(hotel, HotelMapper.mapHotel(hotelModel));
    }

    @Test
    void testMapAmenitiesAndAddToList() {
        List<AmenityModel> amenitiesModel = new ArrayList<>();
        amenitiesModel.add(new AmenityModel(1, "Internet"));
        amenitiesModel.add(new AmenityModel(2, "WiFi"));
        List<Amenity> amenitiesExpected = new ArrayList<>();
        Amenity amenity1 = new Amenity();
        amenity1.setId(1);
        amenity1.setName("Internet");
        amenitiesExpected.add(amenity1);
        Amenity amenity2 = new Amenity();
        amenity2.setId(2);
        amenity2.setName("WiFi");
        amenitiesExpected.add(amenity2);

        List<Amenity> amenitiesMapped = new ArrayList<>();
        HotelMapper.mapAmenitiesAndAddToList(amenitiesModel, amenitiesMapped);
        assertEquals(amenitiesExpected, amenitiesMapped);
    }

    @Test
    void testMapAmenity() {
        AmenityModel amenityModel = new AmenityModel(1, "Internet");
        Amenity amenity = new Amenity();
        amenity.setId(1);
        amenity.setName("Internet");
        assertEquals(amenity, HotelMapper.mapAmenity(amenityModel));
    }
}
