package choice.university.ivan.soapapi.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import choice.university.ivan.schemas.AddAmenityHotelRequest;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.CreateHotelRequest;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelRequest;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsRequest;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetAllAmenitiesRequest;
import choice.university.ivan.schemas.GetAllAmenitiesResponse;
import choice.university.ivan.schemas.GetAllHotelsRequest;
import choice.university.ivan.schemas.GetAllHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdRequest;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Page;
import choice.university.ivan.schemas.RemoveAmenityHotelRequest;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelRequest;
import choice.university.ivan.schemas.UpdateHotelResponse;
import choice.university.ivan.soapapi.mapper.HotelMapper;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.service.AmenityService;
import choice.university.ivan.soapapi.service.HotelService;

@Endpoint
public class HotelEndpoints {
    private static final String NAMESPACE_URI = "choice.university.ivan.schemas";
    @Autowired
    private HotelService hotelService;
    @Autowired
    private AmenityService amenityService;

    @PayloadRoot(localPart = "getHotelByIdRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetHotelByIdResponse getHotelById(@RequestPayload GetHotelByIdRequest request) {
        GetHotelByIdResponse response = new GetHotelByIdResponse();
        Hotel hotel = HotelMapper.mapHotel(hotelService.getById(request.getId()));
        response.setHotel(hotel);
        return response;
    }

    @PayloadRoot(localPart = "getAllHotelsRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetAllHotelsResponse processAllHotelsRequest(@RequestPayload GetAllHotelsRequest request) {
        GetAllHotelsResponse response = new GetAllHotelsResponse();
        Page pageResponse = new Page();
        org.springframework.data.domain.Page<HotelModel> pageFiltered = hotelService.filterHotels("", request.getPage(),
                10);
        pageResponse.setSize(pageFiltered.getSize());
        pageResponse.setPage(pageFiltered.getNumber());
        pageResponse.setTotal(pageFiltered.getTotalElements());
        HotelMapper.mapHotelsList(pageFiltered.getContent(), pageResponse.getItems());
        response.setPage(pageResponse);
        return response;
    }

    @PayloadRoot(localPart = "filterHotelsRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public FilterHotelsResponse processFilterHotelsRequest(@RequestPayload FilterHotelsRequest request) {
        FilterHotelsResponse response = new FilterHotelsResponse();
        Page pageResponse = new Page();
        org.springframework.data.domain.Page<HotelModel> pageFiltered = hotelService.filterHotels(request.getName(),
                request.getPage(), 10);
        pageResponse.setSize(pageFiltered.getSize());
        pageResponse.setPage(pageFiltered.getNumber());
        pageResponse.setTotal(pageFiltered.getTotalElements());
        HotelMapper.mapHotelsList(pageFiltered.getContent(), pageResponse.getItems());
        response.setPage(pageResponse);
        return response;
    }

    @PayloadRoot(localPart = "createHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public CreateHotelResponse processCreateHotelRequest(@RequestPayload CreateHotelRequest request) {
        CreateHotelResponse response = new CreateHotelResponse();
        HotelModel hotel = new HotelModel(null, request.getName(), request.getAddress(), request.getRating());
        HotelModel hotelCreated = hotelService.createHotel(hotel);
        response.setHotel(HotelMapper.mapHotel(hotelCreated));
        return response;
    }

    @PayloadRoot(localPart = "updateHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public UpdateHotelResponse processUpdateHotelRequest(@RequestPayload UpdateHotelRequest request) {
        UpdateHotelResponse response = new UpdateHotelResponse();
        HotelModel hotel = new HotelModel(request.getId(), request.getName(), request.getAddress(),
                request.getRating());
        HotelModel hotelUpdated = hotelService.updateHotel(hotel);
        response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        return response;
    }

    @PayloadRoot(localPart = "deleteHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public DeleteHotelResponse processDeleteHotelRequest(@RequestPayload DeleteHotelRequest request) {
        DeleteHotelResponse response = new DeleteHotelResponse();
        HotelModel hotelDeleted = hotelService.deleteHotel(request.getId());
        response.setHotel(HotelMapper.mapHotel(hotelDeleted));
        return response;
    }

    @PayloadRoot(localPart = "addAmenityHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public AddAmenityHotelResponse processAddAmenityToHotelRequest(@RequestPayload AddAmenityHotelRequest request) {
        AddAmenityHotelResponse response = new AddAmenityHotelResponse();
        HotelModel hotelUpdated = hotelService.addAmenityToHotel(request.getIdHotel(), request.getIdAmenity());
        response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        return response;
    }

    @PayloadRoot(localPart = "removeAmenityHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public RemoveAmenityHotelResponse processRemoveAmenityFromHotelRequest(
            @RequestPayload RemoveAmenityHotelRequest request) {
        RemoveAmenityHotelResponse response = new RemoveAmenityHotelResponse();
        HotelModel hotelUpdated = hotelService.removeAmenityFromHotel(request.getIdHotel(), request.getIdAmenity());
        response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        return response;
    }

    @PayloadRoot(localPart = "getAllAmenitiesRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetAllAmenitiesResponse processGetAllAmenitiesRequest(
            @RequestPayload GetAllAmenitiesRequest request) {
        GetAllAmenitiesResponse response = new GetAllAmenitiesResponse();
        List<AmenityModel> amenities = amenityService.getAll();
        List<Amenity> amenitiesResponse = new ArrayList<>();
        HotelMapper.mapAmenitiesAndAddToList(amenities, amenitiesResponse);
        amenitiesResponse.stream().forEach(a -> response.getAmenities().add(a));
        return response;
    }

}
