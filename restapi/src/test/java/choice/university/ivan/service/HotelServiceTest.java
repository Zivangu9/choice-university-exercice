package choice.university.ivan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;

import choice.university.ivan.client.HotelClient;
import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Page;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelResponse;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class HotelServiceTest {
    @InjectMocks
    private HotelServiceImpl hotelService;
    @Mock
    private HotelClient hotelClient;

    @Test
    public void testContextLoad() {
        assertNotNull(hotelService);
        assertNotNull(hotelClient);
    }

    @Test
    public void testAddAmenityToHotelByIds() {
        AddAmenityHotelResponse addAmenityHotelResponseMock = new AddAmenityHotelResponse();
        addAmenityHotelResponseMock.setHotel(new Hotel());
        when(hotelClient.addAmenityToHotelByIds(1, 1))
                .thenReturn(addAmenityHotelResponseMock);
        List<AmenityModel> amenityModels = hotelService.addAmenityToHotelByIds(1, 1);

        assertNotNull(amenityModels);
    }

    @Test
    public void testCreateHotel() {
        HotelModel hotelToCreate = new HotelModel(0, "New Hotel", "New Hotel Address", 9.7);

        CreateHotelResponse createHotelResponseMock = new CreateHotelResponse();
        Hotel hotelCreatedMock = new Hotel();
        hotelCreatedMock.setId(1);
        hotelCreatedMock.setName("New Hotel");
        hotelCreatedMock.setAddress("New Hotel Address");
        hotelCreatedMock.setRating(9.7);
        createHotelResponseMock.setHotel(hotelCreatedMock);
        when(hotelClient.createHotel(hotelToCreate)).thenReturn(createHotelResponseMock);

        HotelModel hotelCreated = hotelService.createHotel(hotelToCreate);
        assertEquals(hotelCreated, HotelMapper.getHotelModel(hotelCreatedMock));
    }

    @Test
    public void testDeleteHotel() {
        DeleteHotelResponse deleteHotelResponseMock = new DeleteHotelResponse();
        Hotel hotelDeletedMock = new Hotel();
        hotelDeletedMock.setId(1);
        hotelDeletedMock.setName("Hotel Deleted");
        hotelDeletedMock.setAddress("Hotel Deleted Address");
        hotelDeletedMock.setRating(1.4);
        deleteHotelResponseMock.setHotel(hotelDeletedMock);

        when(hotelClient.deleteHotel(1)).thenReturn(deleteHotelResponseMock);
        HotelModel hotelDeleted = hotelService.deleteHotel(1);
        assertNotNull(hotelDeleted);
        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(1);
        hotelModel.setName("Hotel Deleted");
        hotelModel.setAddress("Hotel Deleted Address");
        hotelModel.setRating(1.4);
        hotelModel.setAmenities(new ArrayList<>());
        assertEquals(hotelModel, HotelMapper.getHotelModel(hotelDeletedMock));
    }

    @Test
    public void testFilterHotels() {
        Page pageMock = new Page();
        pageMock.setPage(0);
        pageMock.setSize(10);
        pageMock.setTotal(6);
        FilterHotelsResponse filterHotelsResponseMock = new FilterHotelsResponse();
        filterHotelsResponseMock.setPage(pageMock);
        when(hotelClient.filterHotels("", 0)).thenReturn(filterHotelsResponseMock);

        FilterHotelsResponse filterHotelsResponse = hotelService.filterHotels("", 0);
        assertNotNull(filterHotelsResponse);
        assertEquals(filterHotelsResponseMock, filterHotelsResponse);
    }

    @Test
    public void testGetHotelById() {
        GetHotelByIdResponse getHotelByIdResponseMock = new GetHotelByIdResponse();
        getHotelByIdResponseMock.setHotel(new Hotel());
        when(hotelClient.getHotelById(1)).thenReturn(getHotelByIdResponseMock);
        HotelModel hotelModel = hotelService.getHotelById(1);
        assertNotNull(hotelModel);
    }

    @Test
    public void testRemoveAmenityFromHotelByIds() {
        RemoveAmenityHotelResponse removeAmenityHotelResponseMock = new RemoveAmenityHotelResponse();
        removeAmenityHotelResponseMock.setHotel(new Hotel());
        when(hotelClient.removeAmenityFromHotelByIds(1, 1))
                .thenReturn(removeAmenityHotelResponseMock);
        List<AmenityModel> amenityModels = hotelService.removeAmenityFromHotelByIds(1, 1);
        assertNotNull(amenityModels);
    }

    @Test
    public void testUpdateHotel() {
        HotelModel hotelToUpdate = new HotelModel(1, "Hotel Updated", "Hotel Address", 9.8);
        UpdateHotelResponse updateHotelResponseMock = new UpdateHotelResponse();
        Hotel hotelUpdatedMock = new Hotel();
        hotelUpdatedMock.setId(1);
        hotelUpdatedMock.setName("Hotel Updated");
        hotelUpdatedMock.setAddress("Hotel Address");
        hotelUpdatedMock.setRating(9.8);
        updateHotelResponseMock.setHotel(hotelUpdatedMock);

        when(hotelClient.updateHotel(hotelToUpdate)).thenReturn(updateHotelResponseMock);

        HotelModel hotelUpdated = hotelService.updateHotel(hotelToUpdate);
        assertNotNull(hotelUpdated);
        assertEquals(hotelUpdated, HotelMapper.getHotelModel(hotelUpdatedMock));

    }
}
