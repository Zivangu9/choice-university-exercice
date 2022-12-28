package choice.university.ivan.soapapi.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import choice.university.ivan.schemas.CreateHotelRequest;
import choice.university.ivan.schemas.DeleteHotelRequest;
import choice.university.ivan.schemas.GetHotelRequest;
import choice.university.ivan.schemas.GetHotelResponse;
import choice.university.ivan.schemas.GetHotelsRequest;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Pagination;
import choice.university.ivan.schemas.UpdateHotelRequest;
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

    @PayloadRoot(localPart = "CreateHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetHotelResponse processCreateHotelRequest(@RequestPayload CreateHotelRequest request) {
        GetHotelResponse response = new GetHotelResponse();
        HotelModel hotel = new HotelModel();
        hotel.setId(null);
        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        hotel.setRating(request.getRating());
        HotelModel hotelCreated = hotelService.createHotel(hotel);
        response.setHotel(HotelMapper.mapHotel(hotelCreated));
        return response;
    }

    @PayloadRoot(localPart = "UpdateHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetHotelResponse processUpdateHotelRequest(@RequestPayload UpdateHotelRequest request) {
        GetHotelResponse response = new GetHotelResponse();
        HotelModel hotel = new HotelModel();
        hotel.setId(request.getId());
        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        hotel.setRating(request.getRating());
        HotelModel hotelCreated = hotelService.updateHotel(hotel);
        response.setHotel(HotelMapper.mapHotel(hotelCreated));
        return response;
    }

    @PayloadRoot(localPart = "DeleteHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetHotelResponse processDeleteHotelRequest(@RequestPayload DeleteHotelRequest request) {
        HotelModel hotelDeleted = hotelService.deleteHotel(request.getId());
        GetHotelResponse response = new GetHotelResponse();
        response.setHotel(HotelMapper.mapHotel(hotelDeleted));
        return response;
    }
}
