package choice.university.ivan.soapapi.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import choice.university.ivan.schemas.AddAmenityHotelRequest;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelRequest;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelRequest;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsRequest;
import choice.university.ivan.schemas.FilterHotelsResponse;
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
import choice.university.ivan.soapapi.exception.BadRequestException;
import choice.university.ivan.soapapi.exception.ConflictException;
import choice.university.ivan.soapapi.exception.NotFoundException;
import choice.university.ivan.soapapi.mapper.HotelMapper;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.service.AmenityService;
import choice.university.ivan.soapapi.service.HotelService;

@Endpoint
public class HotelEndpoints {
    private static final String NAMESPACE_URI = "http://localhost:8081/hotels";
    @Autowired
    private HotelService hotelService;
    @Autowired
    private AmenityService amenityService;

    @PayloadRoot(localPart = "getHotelByIdRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetHotelByIdResponse getHotelById(@RequestPayload GetHotelByIdRequest request) {
        GetHotelByIdResponse response = new GetHotelByIdResponse();
        Optional<HotelModel> optionalHotel = hotelService.getById(request.getId());
        if (!optionalHotel.isPresent())
            throw new NotFoundException("Hotel with id: " + request.getId() + " not found");
        Hotel hotel = HotelMapper.mapHotel(optionalHotel.get());
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
        validateHotel(hotel);
        HotelModel hotelCreated = hotelService.createHotel(hotel);
        if (hotelCreated == null)
            throw new ConflictException("Exception while adding Hotel");
        response.setHotel(HotelMapper.mapHotel(hotelCreated));
        return response;
    }

    @PayloadRoot(localPart = "updateHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public UpdateHotelResponse processUpdateHotelRequest(@RequestPayload UpdateHotelRequest request) {
        UpdateHotelResponse response = new UpdateHotelResponse();
        if (!hotelService.hotelWithIdExists(request.getId()))
            throw new NotFoundException("Hotel with id: " + request.getId() + " not found");
        HotelModel hotel = new HotelModel(request.getId(), request.getName(), request.getAddress(),
                request.getRating());
        validateHotel(hotel);
        HotelModel hotelUpdated = hotelService.updateHotel(hotel);
        if (hotelUpdated == null)
            throw new ConflictException("Exception while updating Hotel");
        response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        return response;
    }

    @PayloadRoot(localPart = "deleteHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public DeleteHotelResponse processDeleteHotelRequest(@RequestPayload DeleteHotelRequest request) {
        DeleteHotelResponse response = new DeleteHotelResponse();
        if (!hotelService.hotelWithIdExists(request.getId()))
            throw new NotFoundException("Hotel with id: " + request.getId() + " not found");
        HotelModel hotelDeleted = hotelService.deleteHotel(request.getId());
        if (hotelDeleted == null)
            throw new ConflictException("Exception deleting updating Hotel");
        response.setHotel(HotelMapper.mapHotel(hotelDeleted));
        return response;
    }

    @PayloadRoot(localPart = "addAmenityHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public AddAmenityHotelResponse processAddAmenityToHotelRequest(@RequestPayload AddAmenityHotelRequest request) {
        AddAmenityHotelResponse response = new AddAmenityHotelResponse();
        if (!hotelService.hotelWithIdExists(request.getIdHotel()))
            throw new NotFoundException("Hotel with id: " + request.getIdHotel() + " not found");
        if (!amenityService.amenityWithIdExists(request.getIdAmenity()))
            throw new NotFoundException("Amenity with id: " + request.getIdAmenity() + " not found");
        HotelModel hotelUpdated = hotelService.addAmenityToHotel(request.getIdHotel(), request.getIdAmenity());
        if (hotelUpdated == null)
            throw new ConflictException("Exception while adding Amenity to Hotel");
        response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        return response;
    }

    @PayloadRoot(localPart = "removeAmenityHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public RemoveAmenityHotelResponse processRemoveAmenityFromHotelRequest(
            @RequestPayload RemoveAmenityHotelRequest request) {
        RemoveAmenityHotelResponse response = new RemoveAmenityHotelResponse();
        if (!hotelService.hotelWithIdExists(request.getIdHotel()))
            throw new NotFoundException("Hotel with id: " + request.getIdHotel() + " not found");
        if (!amenityService.amenityWithIdExists(request.getIdAmenity()))
            throw new NotFoundException("Amenity with id: " + request.getIdAmenity() + " not found");
        HotelModel hotelUpdated = hotelService.removeAmenityFromHotel(request.getIdHotel(), request.getIdAmenity());
        if (hotelUpdated == null)
            throw new ConflictException("Exception while removing Amenity from Hotel");
        response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        return response;
    }

    private void validateHotel(HotelModel hotel) {
        if (hotel.getName() == null || hotel.getName().equals(""))
            throw new BadRequestException("Name can't be empty");

        if (hotel.getAddress() == null || hotel.getAddress().equals(""))
            throw new BadRequestException("Address can't be empty");

        if (hotel.getRating() < 0 || hotel.getRating() > 10)
            throw new BadRequestException("Invalid rating. The rating must be a numeric value between 0 and 10");
    }
}
