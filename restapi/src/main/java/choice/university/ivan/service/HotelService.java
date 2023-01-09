package choice.university.ivan.service;

import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelResponse;

public interface HotelService {
    public GetHotelByIdResponse getHotelById(int id);

    public FilterHotelsResponse filterHotels(String name, int page);

    public CreateHotelResponse createHotel(HotelModel hotel);

    public UpdateHotelResponse updateHotel(HotelModel hotel);

    public DeleteHotelResponse deleteHotel(int id);

    public AddAmenityHotelResponse addAmenityToHotelByIds(int idHotel, int idAmenity);

    public RemoveAmenityHotelResponse removeAmenityFromHotelByIds(int idHotel, int idAmenity);
}
