package choice.university.ivan.service;

import java.util.List;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.CreateHotelRequest;
import choice.university.ivan.schemas.DeleteHotelRequest;
import choice.university.ivan.schemas.GetHotelRequest;
import choice.university.ivan.schemas.GetHotelResponse;
import choice.university.ivan.schemas.GetHotelsRequest;
import choice.university.ivan.schemas.Pagination;
import choice.university.ivan.schemas.UpdateHotelRequest;

public class HotelService extends WebServiceGatewaySupport {
    public GetHotelResponse getHotelById(int id) {
        GetHotelRequest request = new GetHotelRequest();
        request.setId(id);

        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public List<Amenity> getAmenitiesByHotelId(int id) {
        GetHotelRequest request = new GetHotelRequest();
        request.setId(id);

        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response.getHotel().getAmenities();
    }

    public Pagination filterHotels(String name, int page) {
        GetHotelsRequest request = new GetHotelsRequest();
        request.setName(name);
        request.setPage(page);

        Pagination response = (Pagination) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public GetHotelResponse createHotel(HotelModel hotel) {
        CreateHotelRequest request = new CreateHotelRequest();
        request.setName(hotel.getName());
        request.setAddress(hotel.getAddress());
        request.setRating(hotel.getRating());

        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public GetHotelResponse updateHotel(HotelModel hotel) {
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setId(hotel.getId());
        request.setName(hotel.getName());
        request.setAddress(hotel.getAddress());
        request.setRating(hotel.getRating());

        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public GetHotelResponse deleteHotel(int id) {
        DeleteHotelRequest request = new DeleteHotelRequest();
        request.setId(id);

        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }
}
