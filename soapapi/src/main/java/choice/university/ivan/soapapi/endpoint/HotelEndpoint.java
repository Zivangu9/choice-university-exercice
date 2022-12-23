package choice.university.ivan.soapapi.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import choice.university.ivan.schemas.GetHotelRequest;
import choice.university.ivan.schemas.GetHotelResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.soapapi.mapper.HotelMapper;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.service.HotelService;

@Endpoint
public class HotelEndpoint {
    @Autowired
    private HotelService hotelService;

    @PayloadRoot(namespace = "http://localhost:8081/hotels", localPart = "GetHotelRequest")
    @ResponsePayload
    public GetHotelResponse processHotelRequest(@RequestPayload GetHotelRequest request) {
        GetHotelResponse response = new GetHotelResponse();
        response.setHotel(null);
        Optional<HotelModel> optionalHotel = hotelService.getById(request.getId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = HotelMapper.mapHotel(optionalHotel.get());
            response.setHotel(hotel);
        }
        return response;
    }
}
