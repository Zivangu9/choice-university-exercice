package choice.university.ivan.soapapi.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
import choice.university.ivan.schemas.RemoveAmenityHotelRequest;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelRequest;
import choice.university.ivan.schemas.UpdateHotelResponse;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.service.AmenityService;
import choice.university.ivan.soapapi.service.HotelService;

@ExtendWith(SpringExtension.class)
public class HotelEndpointsTest {
    @InjectMocks
    private HotelEndpoints hotelEndpoints;
    @Mock
    private HotelService hotelService;
    @Mock
    private AmenityService amenityService;

    @Test
    public void testContextLoads() {
        assertNotNull(hotelEndpoints);
        assertNotNull(hotelService);
        assertNotNull(amenityService);
    }

    @Test
    void testGetHotelById() {
        GetHotelByIdRequest getHotelByIdRequest = new GetHotelByIdRequest();
        getHotelByIdRequest.setId(1);
        when(hotelService.getById(1)).thenReturn(Optional.of(new HotelModel(1, "Hotel Name", "Hotel Address", 7.8)));
        GetHotelByIdResponse getHotelByIdResponse = hotelEndpoints.getHotelById(getHotelByIdRequest);
        assertEquals(getHotelByIdResponse.getServiceStatus().getStatusCode(), 302);
    }

    @Test
    void testGetHotelByIdNotFound() {
        GetHotelByIdRequest getHotelByIdRequest = new GetHotelByIdRequest();
        getHotelByIdRequest.setId(1);
        when(hotelService.getById(1)).thenReturn(Optional.empty());
        GetHotelByIdResponse getHotelByIdResponse = hotelEndpoints.getHotelById(getHotelByIdRequest);
        assertEquals(getHotelByIdResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessAllHotelsRequest() {
        GetAllHotelsRequest getAllHotelsRequest = new GetAllHotelsRequest();
        getAllHotelsRequest.setPage(0);
        List<HotelModel> hotelsFiltered = new ArrayList<>();
        hotelsFiltered.add(new HotelModel(1, "Hotel Name", "Hotel Address", 7.8));
        when(hotelService.filterHotels("", 0, 10)).thenReturn(new PageImpl<>(hotelsFiltered));
        GetAllHotelsResponse getAllHotelsResponse = hotelEndpoints.processAllHotelsRequest(getAllHotelsRequest);
        assertEquals(getAllHotelsResponse.getPage().getTotal(), 1);
    }

    @Test
    void testProcessFilterHotelsRequest() {
        FilterHotelsRequest filterHotelsRequest = new FilterHotelsRequest();
        filterHotelsRequest.setName("");
        filterHotelsRequest.setPage(0);
        List<HotelModel> hotelsFiltered = new ArrayList<>();
        hotelsFiltered.add(new HotelModel(1, "Hotel Name", "Hotel Address", 7.8));
        when(hotelService.filterHotels("", 0, 10)).thenReturn(new PageImpl<>(hotelsFiltered));
        FilterHotelsResponse getAllHotelsResponse = hotelEndpoints.processFilterHotelsRequest(filterHotelsRequest);
        assertEquals(getAllHotelsResponse.getPage().getTotal(), 1);
    }

    @Test
    void testProcessCreateHotelRequest() {
        CreateHotelRequest createHotelRequest = new CreateHotelRequest();
        createHotelRequest.setName("Hotel Name");
        createHotelRequest.setAddress("Hotel Address");
        createHotelRequest.setRating(9.8);
        HotelModel hotelModelToCreate = new HotelModel(null, "Hotel Name", "Hotel Address", 9.8);
        HotelModel hotelModelCreated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.createHotel(hotelModelToCreate)).thenReturn(hotelModelCreated);
        CreateHotelResponse createHotelResponse = hotelEndpoints
                .processCreateHotelRequest(createHotelRequest);
        assertEquals(createHotelResponse.getServiceStatus().getStatusCode(), 201);
    }

    @Test
    void testProcessCreateHotelRequestConflict() {
        CreateHotelRequest createHotelRequest = new CreateHotelRequest();
        createHotelRequest.setName("Hotel Name");
        createHotelRequest.setAddress("Hotel Address");
        createHotelRequest.setRating(9.8);
        HotelModel hotelModelToCreate = new HotelModel(null, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.createHotel(hotelModelToCreate)).thenReturn(null);
        CreateHotelResponse createHotelResponse = hotelEndpoints
                .processCreateHotelRequest(createHotelRequest);
        assertEquals(createHotelResponse.getServiceStatus().getStatusCode(), 409);
    }

    @Test
    void testProcessUpdateHotelRequest() {
        UpdateHotelRequest updateHotelRequest = new UpdateHotelRequest();
        updateHotelRequest.setId(1);
        updateHotelRequest.setName("Hotel Name");
        updateHotelRequest.setAddress("Hotel Address");
        updateHotelRequest.setRating(9.8);
        HotelModel hotelModelToUpdate = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(hotelService.updateHotel(hotelModelToUpdate)).thenReturn(hotelModelUpdated);
        UpdateHotelResponse updateHotelResponse = hotelEndpoints
                .processUpdateHotelRequest(updateHotelRequest);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 200);
    }

    @Test
    void testProcessUpdateHotelRequestNotFound() {
        UpdateHotelRequest updateHotelRequest = new UpdateHotelRequest();
        updateHotelRequest.setId(1);
        updateHotelRequest.setName("Hotel Name");
        updateHotelRequest.setAddress("Hotel Address");
        updateHotelRequest.setRating(9.8);
        when(hotelService.hotelWithIdExists(1)).thenReturn(false);
        UpdateHotelResponse updateHotelResponse = hotelEndpoints
                .processUpdateHotelRequest(updateHotelRequest);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessUpdateHotelRequestConflict() {
        UpdateHotelRequest updateHotelRequest = new UpdateHotelRequest();
        updateHotelRequest.setId(1);
        updateHotelRequest.setName("Hotel Name");
        updateHotelRequest.setAddress("Hotel Address");
        updateHotelRequest.setRating(9.8);
        HotelModel hotelModelToUpdate = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(hotelService.updateHotel(hotelModelToUpdate)).thenReturn(null);
        UpdateHotelResponse updateHotelResponse = hotelEndpoints
                .processUpdateHotelRequest(updateHotelRequest);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 409);
    }

    @Test
    void testProcessDeleteHotelRequest() {
        DeleteHotelRequest deleteHotelRequest = new DeleteHotelRequest();
        deleteHotelRequest.setId(1);
        HotelModel hotelModelDeleted = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(hotelService.deleteHotel(1)).thenReturn(hotelModelDeleted);
        DeleteHotelResponse updateHotelResponse = hotelEndpoints
                .processDeleteHotelRequest(deleteHotelRequest);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 200);
    }

    @Test
    void testProcessDeleteHotelRequestNotFound() {
        DeleteHotelRequest deleteHotelRequest = new DeleteHotelRequest();
        deleteHotelRequest.setId(1);
        when(hotelService.hotelWithIdExists(1)).thenReturn(false);
        DeleteHotelResponse updateHotelResponse = hotelEndpoints
                .processDeleteHotelRequest(deleteHotelRequest);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessDeleteHotelRequestConflict() {
        DeleteHotelRequest deleteHotelRequest = new DeleteHotelRequest();
        deleteHotelRequest.setId(1);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(hotelService.deleteHotel(1)).thenReturn(null);
        DeleteHotelResponse updateHotelResponse = hotelEndpoints
                .processDeleteHotelRequest(deleteHotelRequest);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 409);
    }

    @Test
    void testProcessAddAmenityToHotelRequestRequest() {
        AddAmenityHotelRequest addAmenityHotelRequest = new AddAmenityHotelRequest();
        addAmenityHotelRequest.setIdHotel(1);
        addAmenityHotelRequest.setIdAmenity(1);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        hotelModelUpdated.getAmenities().add(new AmenityModel(1, "Wifi"));
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(amenityService.amenityWithIdExists(1)).thenReturn(true);
        when(hotelService.addAmenityToHotel(1, 1)).thenReturn(hotelModelUpdated);
        AddAmenityHotelResponse addAmenityHotelResponse = hotelEndpoints
                .processAddAmenityToHotelRequest(addAmenityHotelRequest);
        assertEquals(addAmenityHotelResponse.getServiceStatus().getStatusCode(), 201);
    }

    @Test
    void testProcessAddAmenityToHotelRequestHotelNotFound() {
        AddAmenityHotelRequest addAmenityHotelRequest = new AddAmenityHotelRequest();
        addAmenityHotelRequest.setIdHotel(1);
        addAmenityHotelRequest.setIdAmenity(1);
        when(hotelService.hotelWithIdExists(1)).thenReturn(false);
        when(amenityService.amenityWithIdExists(1)).thenReturn(true);
        AddAmenityHotelResponse addAmenityHotelResponse = hotelEndpoints
                .processAddAmenityToHotelRequest(addAmenityHotelRequest);
        assertEquals(addAmenityHotelResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessAddAmenityToHotelRequestAmenityNotFound() {
        AddAmenityHotelRequest addAmenityHotelRequest = new AddAmenityHotelRequest();
        addAmenityHotelRequest.setIdHotel(1);
        addAmenityHotelRequest.setIdAmenity(1);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(amenityService.amenityWithIdExists(1)).thenReturn(false);
        AddAmenityHotelResponse addAmenityHotelResponse = hotelEndpoints
                .processAddAmenityToHotelRequest(addAmenityHotelRequest);
        assertEquals(addAmenityHotelResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessAddAmenityToHotelRequestConflict() {
        AddAmenityHotelRequest addAmenityHotelRequest = new AddAmenityHotelRequest();
        addAmenityHotelRequest.setIdHotel(1);
        addAmenityHotelRequest.setIdAmenity(1);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(amenityService.amenityWithIdExists(1)).thenReturn(true);
        when(hotelService.addAmenityToHotel(1, 1)).thenReturn(null);
        AddAmenityHotelResponse addAmenityHotelResponse = hotelEndpoints
                .processAddAmenityToHotelRequest(addAmenityHotelRequest);
        assertEquals(addAmenityHotelResponse.getServiceStatus().getStatusCode(), 409);
    }

    @Test
    void testProcessRemoveAmenityFromHotelRequest() {
        RemoveAmenityHotelRequest removeAmenityHotelRequest = new RemoveAmenityHotelRequest();
        removeAmenityHotelRequest.setIdHotel(1);
        removeAmenityHotelRequest.setIdAmenity(1);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        hotelModelUpdated.getAmenities().add(new AmenityModel(2, "Internet"));
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(amenityService.amenityWithIdExists(1)).thenReturn(true);
        when(hotelService.removeAmenityFromHotel(1, 1)).thenReturn(hotelModelUpdated);
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelEndpoints
                .processRemoveAmenityFromHotelRequest(removeAmenityHotelRequest);
        assertEquals(removeAmenityHotelResponse.getServiceStatus().getStatusCode(), 200);
    }

    @Test
    void testProcessRemoveAmenityFromHotelRequestHotelNotFound() {
        RemoveAmenityHotelRequest removeAmenityHotelRequest = new RemoveAmenityHotelRequest();
        removeAmenityHotelRequest.setIdHotel(1);
        removeAmenityHotelRequest.setIdAmenity(1);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        hotelModelUpdated.getAmenities().add(new AmenityModel(2, "Internet"));
        when(hotelService.hotelWithIdExists(1)).thenReturn(false);
        when(amenityService.amenityWithIdExists(1)).thenReturn(true);
        when(hotelService.removeAmenityFromHotel(1, 1)).thenReturn(hotelModelUpdated);
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelEndpoints
                .processRemoveAmenityFromHotelRequest(removeAmenityHotelRequest);
        assertEquals(removeAmenityHotelResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessRemoveAmenityFromHotelRequestAmenityNotFound() {
        RemoveAmenityHotelRequest removeAmenityHotelRequest = new RemoveAmenityHotelRequest();
        removeAmenityHotelRequest.setIdHotel(1);
        removeAmenityHotelRequest.setIdAmenity(1);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        hotelModelUpdated.getAmenities().add(new AmenityModel(2, "Internet"));
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(amenityService.amenityWithIdExists(1)).thenReturn(false);
        when(hotelService.removeAmenityFromHotel(1, 1)).thenReturn(hotelModelUpdated);
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelEndpoints
                .processRemoveAmenityFromHotelRequest(removeAmenityHotelRequest);
        assertEquals(removeAmenityHotelResponse.getServiceStatus().getStatusCode(), 404);
    }

    @Test
    void testProcessRemoveAmenityFromHotelRequestConflict() {
        RemoveAmenityHotelRequest removeAmenityHotelRequest = new RemoveAmenityHotelRequest();
        removeAmenityHotelRequest.setIdHotel(1);
        removeAmenityHotelRequest.setIdAmenity(1);
        when(hotelService.hotelWithIdExists(1)).thenReturn(true);
        when(amenityService.amenityWithIdExists(1)).thenReturn(true);
        when(hotelService.removeAmenityFromHotel(1, 1)).thenReturn(null);
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelEndpoints
                .processRemoveAmenityFromHotelRequest(removeAmenityHotelRequest);
        assertEquals(removeAmenityHotelResponse.getServiceStatus().getStatusCode(), 409);
    }

}
