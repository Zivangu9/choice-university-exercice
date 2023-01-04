package choice.university.ivan.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import choice.university.ivan.config.AppConfig;
import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.Hotel;
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

    // @Mock
    // private AppConfig appConfig;
    // private MockMvc mockMvc;

    // @Before
    // public void setup() {
    // this.mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    // }

    @Test
    public void contextLoad() {
        assertNotNull(hotelController);
        assertNotNull(hotelService);
    }

    @Test
    public void getHotelFoundByID() {
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
    public void getHotelNotFoundByID() {
        GetHotelByIdResponse getHotelByIdResponse = new GetHotelByIdResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        getHotelByIdResponse.setServiceStatus(serviceStatus);

        when(hotelService.getHotelById(1)).thenReturn(getHotelByIdResponse);

        ResponseEntity<HotelModel> responseEntity = hotelController.getHotelByID(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void createHotel() {
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
    public void createHotelConflict() {
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
    public void updateHotel() {
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
    public void updateHotelConflict() {
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
    public void updateHotelNotFound() {
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
}
