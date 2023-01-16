package choice.university.ivan.soapapi.service;

import org.springframework.data.domain.Page;

import choice.university.ivan.soapapi.model.HotelModel;

public interface HotelService {
    HotelModel getById(int id);

    Page<HotelModel> filterHotels(String name, int page, int size);

    HotelModel createHotel(HotelModel hotel);

    HotelModel updateHotel(HotelModel hotel);

    HotelModel deleteHotel(int id);

    HotelModel addAmenityToHotel(int idHotel, int idAmenity);

    HotelModel removeAmenityFromHotel(int idHotel, int idAmenity);

    void validateHotel(HotelModel hotel);
}
