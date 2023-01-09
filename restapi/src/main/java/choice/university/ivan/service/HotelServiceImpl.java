package choice.university.ivan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import choice.university.ivan.client.HotelClient;
import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelResponse;

@Service
public class HotelServiceImpl {
    @Autowired
    HotelClient hotelClient;

    public HotelModel getHotelById(int id) {
        GetHotelByIdResponse response = hotelClient.getHotelById(id);
        return HotelMapper.getHotelModel(response.getHotel());
    }

    public FilterHotelsResponse filterHotels(String name, int page) {
        return hotelClient.filterHotels(name, page);
    }

    public HotelModel createHotel(HotelModel hotel) {
        CreateHotelResponse createHotelResponse = hotelClient.createHotel(hotel);
        return HotelMapper.getHotelModel(createHotelResponse.getHotel());
    }

    public HotelModel updateHotel(HotelModel hotel) {
        UpdateHotelResponse updateHotelResponse = hotelClient.updateHotel(hotel);
        HotelModel hotelUpdated = HotelMapper.getHotelModel(updateHotelResponse.getHotel());
        return hotelUpdated;
    }

    public HotelModel deleteHotel(int id) {
        DeleteHotelResponse deleteHotelResponse = hotelClient.deleteHotel(id);
        HotelModel hotelDeleted = HotelMapper.getHotelModel(deleteHotelResponse.getHotel());
        return hotelDeleted;
    }

    public List<AmenityModel> addAmenityToHotelByIds(int idHotel, int idAmenity) {
        AddAmenityHotelResponse addAmenityHotelResponse = hotelClient.addAmenityToHotelByIds(idHotel, idAmenity);
        HotelModel hotelUpdated = HotelMapper.getHotelModel(addAmenityHotelResponse.getHotel());
        return hotelUpdated.getAmenities();
    }

    public List<AmenityModel> removeAmenityFromHotelByIds(int idHotel, int idAmenity) {
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelClient.removeAmenityFromHotelByIds(idHotel,
                idAmenity);
        HotelModel hotelUpdated = HotelMapper.getHotelModel(removeAmenityHotelResponse.getHotel());
        return hotelUpdated.getAmenities();
    }
}
