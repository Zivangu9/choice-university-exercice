package choice.university.ivan.soapapi.mapper;

import java.util.List;

import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;

public class HotelMapper {
    public static Hotel mapHotel(HotelModel hotelModel) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelModel.getId());
        hotel.setName(hotelModel.getName());
        hotel.setAddress(hotelModel.getAddress());
        hotel.setRating(hotelModel.getRating());
        mapAmenitiesAndAddToList(hotelModel.getAmenities(), hotel.getAmenities());
        return hotel;
    }

    public static void mapAmenitiesAndAddToList(List<AmenityModel> amenitiesModel, List<Amenity> amenities) {
        for (AmenityModel amenity : amenitiesModel) {
            Amenity a = mapAmenity(amenity);
            amenities.add(a);
        }
    }

    public static Amenity mapAmenity(AmenityModel amenityModel) {
        Amenity amenity = new Amenity();
        amenity.setId(amenityModel.getId());
        amenity.setName(amenityModel.getName());
        return amenity;
    }
}
