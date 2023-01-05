package choice.university.ivan.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Page;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.ServiceStatus;
import choice.university.ivan.schemas.UpdateHotelResponse;
import choice.university.ivan.service.HotelService;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class HotelControllerTest {
    @InjectMocks
    private HotelController hotelController;

    @Mock
    private HotelService hotelService;

    @Test
    public void testContextLoad() {
        assertNotNull(hotelController);
        assertNotNull(hotelService);
    }

    @Test
    public void testFilterHotels() {
        FilterHotelsResponse filterHotelsResponseMock = new FilterHotelsResponse();
        Page page = new Page();
        page.setPage(0);
        page.setSize(10);
        page.setTotal(6);
        filterHotelsResponseMock.setPage(page);
        List<Hotel> hotels = new ArrayList<>();
        Hotel hotel1 = new Hotel();
        hotel1.setId(1);
        hotel1.setName("Hotel Name");
        hotel1.setAddress("Hotel Address");
        hotel1.setRating(8.9);
        Hotel hotel2 = new Hotel();
        hotel2.setId(2);
        hotel2.setName("Hotel Name");
        hotel2.setAddress("Hotel Address");
        hotel2.setRating(8.9);
        hotels.add(hotel1);
        hotels.add(hotel2);
        page.getItems().add(hotel1);
        page.getItems().add(hotel2);
        when(hotelService.filterHotels("", 0)).thenReturn(filterHotelsResponseMock);

        FilterHotelsResponse filterHotelsResponse = hotelController.filterHotels("", 0);

        assertEquals(filterHotelsResponse.getPage(), page);
        assertEquals(filterHotelsResponse, filterHotelsResponseMock);
        assertEquals(filterHotelsResponse.getPage().getItems(), hotels);
    }

    @Test
    public void testGetHotelByID() {
        GetHotelByIdResponse getHotelByIdResponse = new GetHotelByIdResponse();

        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setAddress("Address");
        hotel.setName("Hotel Name");
        hotel.setRating(8.4);
        getHotelByIdResponse.setHotel(hotel);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        getHotelByIdResponse.setServiceStatus(serviceStatus);
        when(hotelService.getHotelById(1)).thenReturn(getHotelByIdResponse);
        HotelModel hotelModel = HotelMapper.getHotelModel(hotel);

        ResponseEntity<HotelModel> responseEntity = hotelController.getHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertEquals(responseEntity.getBody(), hotelModel);
    }

    @Test
    public void testGetHotelNotFoundByID() {
        GetHotelByIdResponse getHotelByIdResponse = new GetHotelByIdResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        getHotelByIdResponse.setServiceStatus(serviceStatus);

        when(hotelService.getHotelById(1)).thenReturn(getHotelByIdResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.getHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateHotel() {
        CreateHotelResponse createHotelResponse = new CreateHotelResponse();
        HotelModel hotelToCreate = new HotelModel();
        hotelToCreate.setName("Hotel Created");
        hotelToCreate.setAddress("Address");
        hotelToCreate.setRating(7.8);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Created");
        hotel.setAddress("Address");
        hotel.setRating(7.8);
        createHotelResponse.setHotel(hotel);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(201);
        createHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.createHotel(hotelToCreate)).thenReturn(createHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.createHotel(hotelToCreate);
        HotelModel hotelModel = HotelMapper.getHotelModel(hotel);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody(), hotelModel);
    }

    @Test
    public void testCreateHotelConflict() {
        CreateHotelResponse createHotelResponse = new CreateHotelResponse();
        HotelModel hotelToCreate = new HotelModel();
        hotelToCreate.setName("Hotel Created");
        hotelToCreate.setAddress("Address");
        hotelToCreate.setRating(7.8);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        createHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.createHotel(hotelToCreate)).thenReturn(createHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.createHotel(hotelToCreate);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void testUpdateHotel() {
        UpdateHotelResponse updateHotelResponse = new UpdateHotelResponse();
        HotelModel hotelToUpdate = new HotelModel();
        hotelToUpdate.setId(1);
        hotelToUpdate.setName("Hotel Created");
        hotelToUpdate.setAddress("Address");
        hotelToUpdate.setRating(7.8);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Created");
        hotel.setAddress("Address");
        hotel.setRating(7.8);
        updateHotelResponse.setHotel(hotel);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        updateHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.updateHotel(hotelToUpdate)).thenReturn(updateHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.updateHotel(1, hotelToUpdate);
        HotelModel hotelModel = HotelMapper.getHotelModel(hotel);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), hotelModel);
    }

    @Test
    public void testUpdateHotelConflict() {
        UpdateHotelResponse updateHotelResponse = new UpdateHotelResponse();
        HotelModel hotelToUpdate = new HotelModel();
        hotelToUpdate.setName("Hotel Created");
        hotelToUpdate.setAddress("Address");
        hotelToUpdate.setRating(7.8);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        updateHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.updateHotel(hotelToUpdate)).thenReturn(updateHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.updateHotel(1, hotelToUpdate);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void testUpdateHotelNotFound() {
        UpdateHotelResponse updateHotelResponse = new UpdateHotelResponse();
        HotelModel hotelToUpdate = new HotelModel();
        hotelToUpdate.setName("Hotel Created");
        hotelToUpdate.setAddress("Address");
        hotelToUpdate.setRating(7.8);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        updateHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.updateHotel(hotelToUpdate)).thenReturn(updateHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.updateHotel(1, hotelToUpdate);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteHotelByID() {
        DeleteHotelResponse deleteHotelResponse = new DeleteHotelResponse();
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Deleted");
        hotel.setAddress("Address");
        hotel.setRating(7.8);
        deleteHotelResponse.setHotel(hotel);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        deleteHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.deleteHotel(1)).thenReturn(deleteHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.deleteHotelByID(1);
        HotelModel hotelModel = HotelMapper.getHotelModel(hotel);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), hotelModel);
    }

    @Test
    public void testDeleteHotelConflict() {
        DeleteHotelResponse deleteHotelResponse = new DeleteHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        deleteHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.deleteHotel(1)).thenReturn(deleteHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.deleteHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void testDeleteHotelNotFound() {
        DeleteHotelResponse deleteHotelResponse = new DeleteHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        deleteHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.deleteHotel(1)).thenReturn(deleteHotelResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.deleteHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetHotelAmenities() {
        GetHotelByIdResponse getHotelByIdResponse = new GetHotelByIdResponse();
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Name");
        hotel.setAddress("Hotel Address");
        hotel.setRating(8.9);
        List<Amenity> amenities = hotel.getAmenities();
        Amenity amenity1 = new Amenity();
        amenity1.setId(1);
        amenity1.setName("Intenet");
        Amenity amenity2 = new Amenity();
        amenity2.setId(2);
        amenity2.setName("WiFi");
        amenities.add(amenity1);
        amenities.add(amenity2);
        getHotelByIdResponse.setHotel(hotel);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(200);
        getHotelByIdResponse.setServiceStatus(serviceStatus);
        when(hotelService.getHotelById(1)).thenReturn(getHotelByIdResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.getHotelAmenities(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertEquals(responseEntity.getBody(), HotelMapper.getAmenities(amenities));
    }

    @Test
    public void testGetHotelAmenitiesNotFound() {
        GetHotelByIdResponse getHotelByIdResponse = new GetHotelByIdResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        getHotelByIdResponse.setServiceStatus(serviceStatus);
        when(hotelService.getHotelById(1)).thenReturn(getHotelByIdResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.getHotelAmenities(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddHotelAmenityByIds() {
        AddAmenityHotelResponse addAmenityHotelResponse = new AddAmenityHotelResponse();
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Updated");
        hotel.setAddress("Address");
        hotel.setRating(7.9);
        Amenity amenity1 = new Amenity();
        amenity1.setId(1);
        amenity1.setName("Wifi");
        Amenity amenity2 = new Amenity();
        amenity2.setId(2);
        amenity2.setName("Pool");
        hotel.getAmenities().add(amenity1);
        hotel.getAmenities().add(amenity2);
        addAmenityHotelResponse.setHotel(hotel);

        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(201);
        addAmenityHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.addAmenityToHotelByIds(1, 1)).thenReturn(addAmenityHotelResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.addHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), HotelMapper.getAmenities(hotel.getAmenities()));
    }

    @Test
    public void testAddHotelAmenityByIdsNotFound() {
        AddAmenityHotelResponse addAmenityHotelResponse = new AddAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        addAmenityHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.addAmenityToHotelByIds(1, 1)).thenReturn(addAmenityHotelResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.addHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddHotelAmenityByIdsConfict() {
        AddAmenityHotelResponse addAmenityHotelResponse = new AddAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        addAmenityHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.addAmenityToHotelByIds(1, 1)).thenReturn(addAmenityHotelResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.addHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void testRemoveHotelAmenityByIds() {
        RemoveAmenityHotelResponse removeAmenityHotelResponse = new RemoveAmenityHotelResponse();
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel Updated");
        hotel.setAddress("Address");
        hotel.setRating(7.9);
        Amenity amenity1 = new Amenity();
        amenity1.setId(1);
        amenity1.setName("Wifi");
        Amenity amenity2 = new Amenity();
        amenity2.setId(2);
        amenity2.setName("Pool");
        hotel.getAmenities().add(amenity1);
        hotel.getAmenities().add(amenity2);
        removeAmenityHotelResponse.setHotel(hotel);

        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(201);
        removeAmenityHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.removeAmenityFromHotelByIds(1, 1)).thenReturn(removeAmenityHotelResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.removeHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), HotelMapper.getAmenities(hotel.getAmenities()));
    }

    @Test
    public void testRemoveHotelAmenityByIdsNotFound() {
        RemoveAmenityHotelResponse removeAmenityHotelResponse = new RemoveAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        removeAmenityHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.removeAmenityFromHotelByIds(1, 1)).thenReturn(removeAmenityHotelResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.removeHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testRemoveHotelAmenityByIdsConfict() {
        RemoveAmenityHotelResponse removeAmenityHotelResponse = new RemoveAmenityHotelResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        removeAmenityHotelResponse.setServiceStatus(serviceStatus);

        when(hotelService.removeAmenityFromHotelByIds(1, 1)).thenReturn(removeAmenityHotelResponse);

        ResponseEntity<List<AmenityModel>> responseEntity = hotelController.removeHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

}
