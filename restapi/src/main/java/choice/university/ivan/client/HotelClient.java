package choice.university.ivan.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelRequest;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelRequest;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelRequest;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsRequest;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetAllAmenitiesRequest;
import choice.university.ivan.schemas.GetAllAmenitiesResponse;
import choice.university.ivan.schemas.GetHotelByIdRequest;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.RemoveAmenityHotelRequest;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelRequest;
import choice.university.ivan.schemas.UpdateHotelResponse;

public class HotelClient extends WebServiceGatewaySupport {
    public GetHotelByIdResponse getHotelById(int id) {
        GetHotelByIdRequest request = new GetHotelByIdRequest();
        request.setId(id);

        GetHotelByIdResponse response = (GetHotelByIdResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public FilterHotelsResponse filterHotels(String name, int page) {
        FilterHotelsRequest request = new FilterHotelsRequest();
        request.setName(name);
        request.setPage(page);

        FilterHotelsResponse response = (FilterHotelsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public CreateHotelResponse createHotel(HotelModel hotel) {
        CreateHotelRequest request = new CreateHotelRequest();
        request.setName(hotel.getName());
        request.setAddress(hotel.getAddress());
        request.setRating(hotel.getRating());

        CreateHotelResponse response = (CreateHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public UpdateHotelResponse updateHotel(HotelModel hotel) {
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setId(hotel.getId());
        request.setName(hotel.getName());
        request.setAddress(hotel.getAddress());
        request.setRating(hotel.getRating());

        UpdateHotelResponse response = (UpdateHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public DeleteHotelResponse deleteHotel(int id) {
        DeleteHotelRequest request = new DeleteHotelRequest();
        request.setId(id);

        DeleteHotelResponse response = (DeleteHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public AddAmenityHotelResponse addAmenityToHotelByIds(int idHotel, int idAmenity) {
        AddAmenityHotelRequest request = new AddAmenityHotelRequest();
        request.setIdHotel(idHotel);
        request.setIdAmenity(idAmenity);

        AddAmenityHotelResponse response = (AddAmenityHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public RemoveAmenityHotelResponse removeAmenityFromHotelByIds(int idHotel, int idAmenity) {
        RemoveAmenityHotelRequest request = new RemoveAmenityHotelRequest();
        request.setIdHotel(idHotel);
        request.setIdAmenity(idAmenity);

        RemoveAmenityHotelResponse response = (RemoveAmenityHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public GetAllAmenitiesResponse getAllAmenities() {
        GetAllAmenitiesRequest request = new GetAllAmenitiesRequest();
        GetAllAmenitiesResponse response = (GetAllAmenitiesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }
}
