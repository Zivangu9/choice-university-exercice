package choice.university.ivan.soapapi.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import choice.university.ivan.soapapi.exception.ConflictException;
import choice.university.ivan.soapapi.exception.NotFoundException;
import choice.university.ivan.soapapi.mapper.HotelMapper;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.service.AmenityServiceImpl;
import choice.university.ivan.soapapi.service.HotelServiceImpl;

@ExtendWith(SpringExtension.class)
public class HotelEndpointsTest {
    @InjectMocks
    private HotelEndpoints underTest;
    @Mock
    private HotelServiceImpl hotelService;
    @Mock
    private AmenityServiceImpl amenityService;

    @Test
    public void testContextLoads() {
        assertNotNull(underTest);
        assertNotNull(hotelService);
        assertNotNull(amenityService);
    }

    @Test
    void testGetHotelById() {
        GetHotelByIdRequest getHotelByIdRequest = new GetHotelByIdRequest();
        getHotelByIdRequest.setId(1);
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 7.8);
        when(hotelService.getById(1)).thenReturn(hotelModel);
        GetHotelByIdResponse getHotelByIdResponse = underTest.getHotelById(getHotelByIdRequest);
        assertEquals(getHotelByIdResponse.getHotel(), HotelMapper.mapHotel(hotelModel));
    }

    @Test
    void testGetHotelByIdNotFound() {
        GetHotelByIdRequest getHotelByIdRequest = new GetHotelByIdRequest();
        getHotelByIdRequest.setId(1);
        when(hotelService.getById(1)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> underTest.getHotelById(getHotelByIdRequest));
    }

    @Test
    void testProcessAllHotelsRequest() {
        GetAllHotelsRequest getAllHotelsRequest = new GetAllHotelsRequest();
        getAllHotelsRequest.setPage(0);
        List<HotelModel> hotelsFiltered = new ArrayList<>();
        hotelsFiltered.add(new HotelModel(1, "Hotel Name", "Hotel Address", 7.8));
        when(hotelService.filterHotels("", 0, 10)).thenReturn(new PageImpl<>(hotelsFiltered));
        GetAllHotelsResponse getAllHotelsResponse = underTest.processAllHotelsRequest(getAllHotelsRequest);
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
        FilterHotelsResponse getAllHotelsResponse = underTest.processFilterHotelsRequest(filterHotelsRequest);
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
        CreateHotelResponse createHotelResponse = underTest
                .processCreateHotelRequest(createHotelRequest);
        assertEquals(createHotelResponse.getHotel(), HotelMapper.mapHotel(hotelModelCreated));
    }

    @Test
    void testProcessCreateHotelRequestConflict() {
        CreateHotelRequest createHotelRequest = new CreateHotelRequest();
        createHotelRequest.setName("Hotel Name");
        createHotelRequest.setAddress("Hotel Address");
        createHotelRequest.setRating(9.8);
        HotelModel hotelModelToCreate = new HotelModel(null, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.createHotel(hotelModelToCreate)).thenThrow(ConflictException.class);
        assertThrows(ConflictException.class, () -> underTest.processCreateHotelRequest(createHotelRequest));
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
        when(hotelService.getById(1)).thenReturn(hotelModelToUpdate);
        when(hotelService.updateHotel(hotelModelToUpdate)).thenReturn(hotelModelUpdated);
        UpdateHotelResponse updateHotelResponse = underTest
                .processUpdateHotelRequest(updateHotelRequest);
        assertEquals(updateHotelResponse.getHotel(), HotelMapper.mapHotel(hotelModelUpdated));
    }

    @Test
    void testProcessDeleteHotelRequest() {
        DeleteHotelRequest deleteHotelRequest = new DeleteHotelRequest();
        deleteHotelRequest.setId(1);
        HotelModel hotelModelDeleted = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        when(hotelService.getById(1)).thenReturn(hotelModelDeleted);
        when(hotelService.deleteHotel(1)).thenReturn(hotelModelDeleted);
        DeleteHotelResponse updateHotelResponse = underTest
                .processDeleteHotelRequest(deleteHotelRequest);
        assertEquals(updateHotelResponse.getHotel(), HotelMapper.mapHotel(hotelModelDeleted));
    }

    @Test
    void testProcessAddAmenityToHotelRequestRequest() {
        AddAmenityHotelRequest addAmenityHotelRequest = new AddAmenityHotelRequest();
        addAmenityHotelRequest.setIdHotel(1);
        addAmenityHotelRequest.setIdAmenity(1);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        AmenityModel amenityModelToAdd = new AmenityModel(1, "Wifi");
        hotelModelUpdated.getAmenities().add(amenityModelToAdd);
        when(hotelService.getById(1)).thenReturn(hotelModelUpdated);
        when(amenityService.getById(1)).thenReturn(amenityModelToAdd);
        when(hotelService.addAmenityToHotel(1, 1)).thenReturn(hotelModelUpdated);
        AddAmenityHotelResponse addAmenityHotelResponse = underTest
                .processAddAmenityToHotelRequest(addAmenityHotelRequest);
        assertEquals(addAmenityHotelResponse.getHotel(), HotelMapper.mapHotel(hotelModelUpdated));
    }

    @Test
    void testProcessRemoveAmenityFromHotelRequest() {
        RemoveAmenityHotelRequest removeAmenityHotelRequest = new RemoveAmenityHotelRequest();
        removeAmenityHotelRequest.setIdHotel(1);
        removeAmenityHotelRequest.setIdAmenity(1);
        HotelModel hotelModelUpdated = new HotelModel(1, "Hotel Name", "Hotel Address", 9.8);
        AmenityModel amenityModelToRemove = new AmenityModel(2, "Internet");
        hotelModelUpdated.getAmenities().add(amenityModelToRemove);
        when(hotelService.getById(1)).thenReturn(hotelModelUpdated);
        when(amenityService.getById(1)).thenReturn(amenityModelToRemove);
        when(hotelService.removeAmenityFromHotel(1, 1)).thenReturn(hotelModelUpdated);
        RemoveAmenityHotelResponse removeAmenityHotelResponse = underTest
                .processRemoveAmenityFromHotelRequest(removeAmenityHotelRequest);
        assertEquals(removeAmenityHotelResponse.getHotel(), HotelMapper.mapHotel(hotelModelUpdated));
    }

}
