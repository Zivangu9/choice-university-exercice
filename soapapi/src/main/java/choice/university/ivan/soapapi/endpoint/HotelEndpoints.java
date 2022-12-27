package choice.university.ivan.soapapi.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import choice.university.ivan.schemas.GetHotelRequest;
import choice.university.ivan.schemas.GetHotelResponse;
import choice.university.ivan.schemas.GetHotelsRequest;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Pagination;
import choice.university.ivan.soapapi.mapper.HotelMapper;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.service.HotelService;

@Endpoint
public class HotelEndpoints {
    private static final String NAMESPACE_URI = "http://localhost:8081/hotels";
    @Autowired
    private HotelService hotelService;

    @PayloadRoot(localPart = "GetHotelRequest", namespace = NAMESPACE_URI)
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

    @PayloadRoot(localPart = "GetAllHotelsRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public Pagination processAllHotelsRequest() {
        Pagination response = new Pagination();
        Page<HotelModel> pageHotels = hotelService.filterHotels("", 0, 10);
        response.setSize(pageHotels.getSize());
        response.setPage(pageHotels.getNumber());
        response.setTotal(pageHotels.getTotalElements());
        HotelMapper.mapHotelsList(pageHotels.getContent(), response.getItems());
        return response;
    }

    @PayloadRoot(localPart = "GetHotelsRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public Pagination processFilterHotelsRequest(@RequestPayload GetHotelsRequest request) {
        Pagination response = new Pagination();
        Page<HotelModel> pageHotels = hotelService.filterHotels(request.getName(), request.getPage(), 10);
        response.setSize(pageHotels.getSize());
        response.setPage(pageHotels.getNumber());
        response.setTotal(pageHotels.getTotalElements());
        HotelMapper.mapHotelsList(pageHotels.getContent(), response.getItems());
        return response;
    }

}
