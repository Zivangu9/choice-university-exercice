package choice.university.ivan.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.Hotel;

public class HotelMapperTest {

    @Test
    public void testConstructor() {
        assertNotNull(new HotelMapper());
    }

    @Test
    public void testGetAmenities() {
        List<Amenity> amenities = new ArrayList<>();
        Amenity a1 = new Amenity();
        a1.setId(1);
        a1.setName("Internet");
        Amenity a2 = new Amenity();
        a2.setId(2);
        a2.setName("WiFi");
        amenities.add(a1);
        amenities.add(a2);

        List<AmenityModel> amenityModels = new ArrayList<>();
        AmenityModel am1 = new AmenityModel();
        am1.setId(1);
        am1.setName("Internet");
        AmenityModel am2 = new AmenityModel();
        am2.setId(2);
        am2.setName("WiFi");
        amenityModels.add(am1);
        amenityModels.add(am2);

        assertEquals(HotelMapper.getAmenities(amenities), amenityModels);
    }

    @Test
    public void testGetAmenity() {
        Amenity amenity = new Amenity();
        amenity.setId(1);
        amenity.setName("Internet");
        AmenityModel amenityModel = new AmenityModel();
        amenityModel.setId(1);
        amenityModel.setName("Internet");
        assertEquals(HotelMapper.getAmenity(amenity), amenityModel);
    }

    @Test
    public void testGetHotelModel() {
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Name");
        hotel.setAddress("Hotel Address");
        hotel.setRating(7.9);

        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(1);
        hotelModel.setName("Hotel Name");
        hotelModel.setAddress("Hotel Address");
        hotelModel.setRating(7.9);
        hotelModel.setAmenities(new ArrayList<>());
        assertEquals(HotelMapper.getHotelModel(hotel), hotelModel);
    }
}
