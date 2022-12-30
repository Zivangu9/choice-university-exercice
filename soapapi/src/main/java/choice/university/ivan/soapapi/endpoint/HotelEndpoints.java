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
import choice.university.ivan.schemas.ServiceStatus;
import choice.university.ivan.schemas.UpdateHotelRequest;
import choice.university.ivan.schemas.UpdateHotelResponse;
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
        ServiceStatus serviceStatus = new ServiceStatus();
        Optional<HotelModel> optionalHotel = hotelService.getById(request.getId());
        serviceStatus.setStatusCode(404);
        serviceStatus.setStatusName("NOT FOUND");
        serviceStatus.setMessage("Hotel with id: " + request.getId() + " not found");
        if (optionalHotel.isPresent()) {
            Hotel hotel = HotelMapper.mapHotel(optionalHotel.get());
            response.setHotel(hotel);
            serviceStatus.setStatusCode(302);
            serviceStatus.setStatusName("SUCCESS");
            serviceStatus.setMessage("Hotel with id: " + request.getId() + " was found");
        }
        response.setServiceStatus(serviceStatus);
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
        ServiceStatus serviceStatus = new ServiceStatus();
        HotelModel hotel = new HotelModel(null, request.getName(), request.getAddress(), request.getRating());
        HotelModel hotelCreated = hotelService.createHotel(hotel);
        serviceStatus.setStatusCode(201);
        serviceStatus.setStatusName("SUCCESS");
        serviceStatus.setMessage("Hotel Added Successfully");
        if (hotelCreated == null) {
            serviceStatus.setStatusCode(409);
            serviceStatus.setStatusName("CONFLICT");
            serviceStatus.setMessage("Exception while adding Hotel");
        } else
            response.setHotel(HotelMapper.mapHotel(hotelCreated));
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(localPart = "updateHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public UpdateHotelResponse processUpdateHotelRequest(@RequestPayload UpdateHotelRequest request) {
        UpdateHotelResponse response = new UpdateHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if (!hotelService.hotelWithIdExists(request.getId())) {
            serviceStatus.setStatusCode(404);
            serviceStatus.setStatusName("NOT FOUND");
            serviceStatus.setMessage("Hotel with id: " + request.getId() + " not found");
            response.setServiceStatus(serviceStatus);
            return response;
        }
        HotelModel hotel = new HotelModel(request.getId(), request.getName(), request.getAddress(),
                request.getRating());
        HotelModel hotelUpdated = hotelService.updateHotel(hotel);
        serviceStatus.setStatusCode(200);
        serviceStatus.setStatusName("SUCCESS");
        serviceStatus.setMessage("Hotel updated Successfully");
        if (hotelUpdated == null) {
            serviceStatus.setStatusCode(409);
            serviceStatus.setStatusName("CONFLICT");
            serviceStatus.setMessage("Exception while updating Hotel");
        } else
            response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(localPart = "deleteHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public DeleteHotelResponse processDeleteHotelRequest(@RequestPayload DeleteHotelRequest request) {
        DeleteHotelResponse response = new DeleteHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if (!hotelService.hotelWithIdExists(request.getId())) {
            serviceStatus.setStatusCode(404);
            serviceStatus.setStatusName("NOT FOUND");
            serviceStatus.setMessage("Hotel with id: " + request.getId() + " not found");
            response.setServiceStatus(serviceStatus);
            return response;
        }
        HotelModel hotelDeleted = hotelService.deleteHotel(request.getId());
        serviceStatus.setStatusCode(200);
        serviceStatus.setStatusName("SUCCESS");
        serviceStatus.setMessage("Hotel deleted Successfully");
        if (hotelDeleted == null) {
            serviceStatus.setStatusCode(409);
            serviceStatus.setStatusName("CONFLICT");
            serviceStatus.setMessage("Exception while deleting Hotel");
        } else
            response.setHotel(HotelMapper.mapHotel(hotelDeleted));
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(localPart = "addAmenityHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public AddAmenityHotelResponse processAddAmenityToHotelRequest(@RequestPayload AddAmenityHotelRequest request) {
        AddAmenityHotelResponse response = new AddAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if (!hotelService.hotelWithIdExists(request.getIdHotel())) {
            serviceStatus.setStatusCode(404);
            serviceStatus.setStatusName("NOT FOUND");
            serviceStatus.setMessage("Hotel with id: " + request.getIdHotel() + " not found");
            response.setServiceStatus(serviceStatus);
            return response;
        }
        if (!amenityService.amenityWithIdExists(request.getIdAmenity())) {
            serviceStatus.setStatusCode(404);
            serviceStatus.setStatusName("NOT FOUND");
            serviceStatus.setMessage("Amenity with id: " + request.getIdAmenity() + " not found");
            response.setServiceStatus(serviceStatus);
            return response;
        }
        HotelModel hotelUpdated = hotelService.addAmenityToHotel(request.getIdHotel(), request.getIdAmenity());
        serviceStatus.setStatusCode(201);
        serviceStatus.setStatusName("SUCCESS");
        serviceStatus.setMessage("Amenity added to Hotel Successfully");
        if (hotelUpdated == null) {
            serviceStatus.setStatusCode(409);
            serviceStatus.setStatusName("CONFLICT");
            serviceStatus.setMessage("Exception while adding Amenity to Hotel");
        } else
            response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(localPart = "removeAmenityHotelRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public RemoveAmenityHotelResponse processRemoveAmenityFromHotelRequest(
            @RequestPayload RemoveAmenityHotelRequest request) {
        RemoveAmenityHotelResponse response = new RemoveAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if (!hotelService.hotelWithIdExists(request.getIdHotel())) {
            serviceStatus.setStatusCode(404);
            serviceStatus.setStatusName("NOT FOUND");
            serviceStatus.setMessage("Hotel with id: " + request.getIdHotel() + " not found");
            response.setServiceStatus(serviceStatus);
            return response;
        }
        if (!amenityService.amenityWithIdExists(request.getIdAmenity())) {
            serviceStatus.setStatusCode(404);
            serviceStatus.setStatusName("NOT FOUND");
            serviceStatus.setMessage("Amenity with id: " + request.getIdAmenity() + " not found");
            response.setServiceStatus(serviceStatus);
            return response;
        }
        HotelModel hotelUpdated = hotelService.removeAmenityFromHotel(request.getIdHotel(), request.getIdAmenity());
        serviceStatus.setStatusCode(200);
        serviceStatus.setStatusName("SUCCESS");
        serviceStatus.setMessage("Amenity removed from Hotel Successfully");
        if (hotelUpdated == null) {
            serviceStatus.setStatusCode(409);
            serviceStatus.setStatusName("CONFLICT");
            serviceStatus.setMessage("Exception while removing Amenity from Hotel");
        } else
            response.setHotel(HotelMapper.mapHotel(hotelUpdated));
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
