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

import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Page;
import choice.university.ivan.service.HotelServiceImpl;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class HotelControllerTest {
    @InjectMocks
    private HotelController underTest;

    @Mock
    private HotelServiceImpl hotelService;

    @Test
    public void testContextLoads() {
        assertNotNull(underTest);
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

        FilterHotelsResponse filterHotelsResponse = underTest.filterHotels("", 0);

        assertEquals(filterHotelsResponse.getPage(), page);
        assertEquals(filterHotelsResponse, filterHotelsResponseMock);
        assertEquals(filterHotelsResponse.getPage().getItems(), hotels);
    }

    @Test
    public void testGetHotelByID() {
        HotelModel hotel = new HotelModel(1, "Address", "Hotel Name", 8.4);
        when(hotelService.getHotelById(1)).thenReturn(hotel);

        ResponseEntity<HotelModel> responseEntity = underTest.getHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertEquals(responseEntity.getBody(), hotel);
    }

    @Test
    public void testCreateHotel() {
        HotelModel hotelToCreate = new HotelModel(0, "Hotel Created", "Address", 7.8);
        HotelModel hotelCreated = new HotelModel(1, "Hotel Created", "Address", 7.8);
        when(hotelService.createHotel(hotelToCreate)).thenReturn(hotelCreated);

        ResponseEntity<HotelModel> responseEntity = underTest.createHotel(hotelToCreate);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody(), hotelCreated);
    }

    @Test
    public void testUpdateHotel() {
        HotelModel hotelToUpdate = new HotelModel(1, "Hotel Created", "Address", 7.8);
        when(hotelService.updateHotel(hotelToUpdate)).thenReturn(hotelToUpdate);

        ResponseEntity<HotelModel> responseEntity = underTest.updateHotel(1, hotelToUpdate);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), hotelToUpdate);
    }

    @Test
    public void testDeleteHotelByID() {
        HotelModel hotelDeleted = new HotelModel(1, "Hotel Deleted", "Address", 7.8);
        when(hotelService.deleteHotel(1)).thenReturn(hotelDeleted);

        ResponseEntity<HotelModel> responseEntity = underTest.deleteHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), hotelDeleted);
    }

    @Test
    public void testGetHotelAmenities() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Name", "Hotel Address", 8.9);
        List<AmenityModel> amenities = new ArrayList<>();
        AmenityModel amenity1 = new AmenityModel(1, "Intenet");
        AmenityModel amenity2 = new AmenityModel(2, "WiFi");
        amenities.add(amenity1);
        amenities.add(amenity2);
        hotelModel.setAmenities(amenities);
        when(hotelService.getHotelById(1)).thenReturn(hotelModel);

        ResponseEntity<List<AmenityModel>> responseEntity = underTest.getHotelAmenities(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertEquals(responseEntity.getBody(), amenities);
    }

    @Test
    public void testAddHotelAmenityByIds() {
        AmenityModel amenity1 = new AmenityModel(1, "Wifi");
        AmenityModel amenity2 = new AmenityModel(2, "Pool");
        List<AmenityModel> amenityModels = new ArrayList<>();
        amenityModels.add(amenity1);
        amenityModels.add(amenity2);
        when(hotelService.addAmenityToHotelByIds(1, 1)).thenReturn(amenityModels);

        ResponseEntity<List<AmenityModel>> responseEntity = underTest.addHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), amenityModels);
    }

    @Test
    public void testRemoveHotelAmenityByIds() {
        AmenityModel amenity1 = new AmenityModel(1, "Wifi");
        AmenityModel amenity2 = new AmenityModel(2, "Pool");
        List<AmenityModel> amenityModels = new ArrayList<>();
        amenityModels.add(amenity1);
        amenityModels.add(amenity2);

        when(hotelService.removeAmenityFromHotelByIds(1, 1)).thenReturn(amenityModels);

        ResponseEntity<List<AmenityModel>> responseEntity = underTest.removeHotelAmenityByIds(1, 1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), amenityModels);
    }

    @Test
    public void testGetAllAmenities() {
        List<AmenityModel> amenityModels = new ArrayList<>();
        amenityModels.add(new AmenityModel(1, "Wifi"));
        amenityModels.add(new AmenityModel(2, "Pool"));

        when(hotelService.getAllAmenities()).thenReturn(amenityModels);

        ResponseEntity<List<AmenityModel>> responseEntity = underTest.getAllAmenities();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertEquals(responseEntity.getBody(), amenityModels);
    }

}
