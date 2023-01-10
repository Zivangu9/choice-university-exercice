package choice.university.ivan.service;

import java.util.List;

import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.FilterHotelsResponse;

public interface HotelService {
    public HotelModel getHotelById(int id);

    public FilterHotelsResponse filterHotels(String name, int page);

    public HotelModel createHotel(HotelModel hotel);

    public HotelModel updateHotel(HotelModel hotel);

    public HotelModel deleteHotel(int id);

    public List<AmenityModel> addAmenityToHotelByIds(int idHotel, int idAmenity);

    public List<AmenityModel> removeAmenityFromHotelByIds(int idHotel, int idAmenity);

    public List<AmenityModel> getAllAmenities();

    public void validateHotel(HotelModel hotel);
}
