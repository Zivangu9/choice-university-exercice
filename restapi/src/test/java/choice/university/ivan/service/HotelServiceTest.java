package choice.university.ivan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ws.client.core.WebServiceTemplate;

import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelRequest;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelRequest;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelRequest;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsRequest;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdRequest;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Page;
import choice.university.ivan.schemas.RemoveAmenityHotelRequest;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.ServiceStatus;
import choice.university.ivan.schemas.UpdateHotelRequest;
import choice.university.ivan.schemas.UpdateHotelResponse;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class HotelServiceTest {
    @InjectMocks
    HotelService hotelService;
    @Mock
    WebServiceTemplate webServiceTemplate;

    @Test
    public void testContextLoad() {
        assertNotNull(hotelService);
    }

    @Test
    public void testAddAmenityToHotelByIds() {
        AddAmenityHotelRequest addAmenityHotelRequestMock = new AddAmenityHotelRequest();
        addAmenityHotelRequestMock.setIdAmenity(1);
        addAmenityHotelRequestMock.setIdHotel(1);
        AddAmenityHotelResponse addAmenityHotelResponseMock = new AddAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(201);
        addAmenityHotelResponseMock.setServiceStatus(serviceStatus);

        when(webServiceTemplate.marshalSendAndReceive(addAmenityHotelRequestMock))
                .thenReturn(addAmenityHotelResponseMock);
        AddAmenityHotelResponse removeAmenityHotelResponse = hotelService.addAmenityToHotelByIds(1, 1);

        assertNotNull(removeAmenityHotelResponse);
        assertEquals(removeAmenityHotelResponse.getServiceStatus().getStatusCode(), 201);

    }

    @Test
    public void testCreateHotel() {
        CreateHotelRequest createHotelRequestMock = new CreateHotelRequest();
        createHotelRequestMock.setName("New Hotel");
        createHotelRequestMock.setAddress("New Hotel Address");
        createHotelRequestMock.setRating(9.7);
        CreateHotelResponse createHotelResponseMock = new CreateHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(201);
        createHotelResponseMock.setServiceStatus(serviceStatus);
        Hotel hotelCreated = new Hotel();
        hotelCreated.setId(1);
        hotelCreated.setName("New Hotel");
        hotelCreated.setAddress("New Hotel Address");
        hotelCreated.setRating(9.7);
        createHotelResponseMock.setHotel(hotelCreated);
        when(webServiceTemplate.marshalSendAndReceive(createHotelRequestMock)).thenReturn(createHotelResponseMock);

        HotelModel hotelModel = new HotelModel(0, "New Hotel", "New Hotel Address");
        hotelModel.setRating(9.7);
        hotelModel.setAmenities(new ArrayList<>());
        CreateHotelResponse createHotelResponse = hotelService.createHotel(hotelModel);
        assertEquals(createHotelResponse, createHotelResponseMock);
        assertEquals(createHotelResponse.getServiceStatus().getStatusCode(), 201);
        hotelModel.setId(1);
        assertEquals(hotelModel, HotelMapper.getHotelModel(createHotelResponse.getHotel()));
    }

    @Test
    public void testDeleteHotel() {
        DeleteHotelRequest deleteHotelRequestMock = new DeleteHotelRequest();
        deleteHotelRequestMock.setId(1);
        DeleteHotelResponse deleteHotelResponseMock = new DeleteHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        deleteHotelResponseMock.setServiceStatus(serviceStatus);

        Hotel hotelDeleted = new Hotel();
        hotelDeleted.setId(1);
        hotelDeleted.setName("Hotel Deleted");
        hotelDeleted.setAddress("Hotel Deleted Address");
        hotelDeleted.setRating(1.4);
        deleteHotelResponseMock.setHotel(hotelDeleted);

        when(webServiceTemplate.marshalSendAndReceive(deleteHotelRequestMock)).thenReturn(deleteHotelResponseMock);
        DeleteHotelResponse deleteHotelResponse = hotelService.deleteHotel(1);
        assertNotNull(deleteHotelResponse);
        assertEquals(deleteHotelResponse.getServiceStatus().getStatusCode(), 200);
        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(1);
        hotelModel.setName("Hotel Deleted");
        hotelModel.setAddress("Hotel Deleted Address");
        hotelModel.setRating(1.4);
        hotelModel.setAmenities(new ArrayList<>());
        assertEquals(HotelMapper.getHotelModel(deleteHotelResponse.getHotel()), hotelModel);
    }

    @Test
    public void testFilterHotels() {
        FilterHotelsRequest filterHotelsRequestMock = new FilterHotelsRequest();
        filterHotelsRequestMock.setName("");
        filterHotelsRequestMock.setPage(0);
        Page pageMock = new Page();
        pageMock.setPage(0);
        pageMock.setSize(10);
        pageMock.setTotal(6);
        FilterHotelsResponse filterHotelsResponseMock = new FilterHotelsResponse();
        filterHotelsResponseMock.setPage(pageMock);
        when(webServiceTemplate.marshalSendAndReceive(filterHotelsRequestMock)).thenReturn(filterHotelsResponseMock);

        FilterHotelsResponse filterHotelsResponse = hotelService.filterHotels("", 0);
        assertNotNull(filterHotelsResponse);
        assertEquals(filterHotelsResponseMock, filterHotelsResponse);
    }

    @Test
    public void testGetHotelById() {
        GetHotelByIdRequest getHotelByIdRequestMock = new GetHotelByIdRequest();
        getHotelByIdRequestMock.setId(1);
        GetHotelByIdResponse getHotelByIdResponseMock = new GetHotelByIdResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(202);
        getHotelByIdResponseMock.setServiceStatus(serviceStatus);
        when(webServiceTemplate.marshalSendAndReceive(getHotelByIdRequestMock)).thenReturn(getHotelByIdResponseMock);
        GetHotelByIdResponse getHotelByIdResponse = hotelService.getHotelById(1);
        assertNotNull(getHotelByIdResponse);
        assertEquals(getHotelByIdResponse.getServiceStatus().getStatusCode(), 202);
    }

    @Test
    public void testRemoveAmenityFromHotelByIds() {
        RemoveAmenityHotelRequest removeAmenityHotelRequestMock = new RemoveAmenityHotelRequest();
        removeAmenityHotelRequestMock.setIdAmenity(1);
        removeAmenityHotelRequestMock.setIdHotel(1);
        RemoveAmenityHotelResponse removeAmenityHotelResponseMock = new RemoveAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        removeAmenityHotelResponseMock.setServiceStatus(serviceStatus);

        when(webServiceTemplate.marshalSendAndReceive(removeAmenityHotelRequestMock))
                .thenReturn(removeAmenityHotelResponseMock);
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelService.removeAmenityFromHotelByIds(1, 1);
        assertNotNull(removeAmenityHotelResponse);
        assertEquals(removeAmenityHotelResponse.getServiceStatus().getStatusCode(), 200);
    }

    @Test
    public void testUpdateHotel() {
        UpdateHotelRequest updateHotelRequestMock = new UpdateHotelRequest();
        updateHotelRequestMock.setId(1);
        updateHotelRequestMock.setName("Hotel Updated");
        updateHotelRequestMock.setAddress("Hotel Address");
        updateHotelRequestMock.setRating(9.8);
        UpdateHotelResponse updateHotelResponseMock = new UpdateHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        updateHotelResponseMock.setServiceStatus(serviceStatus);
        Hotel hotelUpdated = new Hotel();
        hotelUpdated.setId(1);
        hotelUpdated.setName("Hotel Updated");
        hotelUpdated.setAddress("Hotel Address");
        hotelUpdated.setRating(9.8);
        updateHotelResponseMock.setHotel(hotelUpdated);

        when(webServiceTemplate.marshalSendAndReceive(updateHotelRequestMock)).thenReturn(updateHotelResponseMock);
        HotelModel hotelModel = new HotelModel();
        hotelModel.setId(1);
        hotelModel.setName("Hotel Updated");
        hotelModel.setAddress("Hotel Address");
        hotelModel.setRating(9.8);
        hotelModel.setAmenities(new ArrayList<>());
        UpdateHotelResponse updateHotelResponse = hotelService.updateHotel(hotelModel);
        assertNotNull(updateHotelResponse);
        assertEquals(updateHotelResponse.getServiceStatus().getStatusCode(), 200);
        assertEquals(hotelModel, HotelMapper.getHotelModel(updateHotelResponse.getHotel()));

    }
}
