package choice.university.ivan.soapapi.service;

import org.springframework.data.domain.Page;

import choice.university.ivan.soapapi.model.HotelModel;

public interface HotelService {
    public HotelModel getById(int id);

    public Page<HotelModel> filterHotels(String name, int page, int size);

    public HotelModel createHotel(HotelModel hotel);

    public HotelModel updateHotel(HotelModel hotel);

    public HotelModel deleteHotel(int id);

    public HotelModel addAmenityToHotel(int idHotel, int idAmenity);

    public HotelModel removeAmenityFromHotel(int idHotel, int idAmenity);

    public void validateHotel(HotelModel hotel);
}
