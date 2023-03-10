package choice.university.ivan.mapper;

import java.util.ArrayList;
import java.util.List;

import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.Hotel;

public class HotelMapper {

    public static HotelModel getHotelModel(Hotel hotel) {
        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(hotel.getId());
        hotelModel.setName(hotel.getName());
        hotelModel.setAddress(hotel.getAddress());
        hotelModel.setRating(hotel.getRating());
        hotelModel.setAmenities(getAmenities(hotel.getAmenities()));
        return hotelModel;
    }

    public static List<AmenityModel> getAmenities(List<Amenity> amenities) {
        List<AmenityModel> amenitiesModel = new ArrayList<>();
        for (Amenity amenity : amenities) {
            amenitiesModel.add(getAmenity(amenity));
        }
        return amenitiesModel;
    }

    public static AmenityModel getAmenity(Amenity amenitySchema) {
        AmenityModel amenityModel = new AmenityModel(amenitySchema.getId(),
                amenitySchema.getName());
        return amenityModel;
    }
}
