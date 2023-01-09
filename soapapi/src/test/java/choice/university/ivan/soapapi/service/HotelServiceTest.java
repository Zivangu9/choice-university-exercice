package choice.university.ivan.soapapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import choice.university.ivan.soapapi.exception.ConflictException;
import choice.university.ivan.soapapi.exception.NotFoundException;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.repository.AmenityRepository;
import choice.university.ivan.soapapi.repository.HotelRepository;

@ExtendWith(SpringExtension.class)
public class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private AmenityService amenityService;
    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void contextLoads() {
        assertNotNull(hotelService);
    }

    @Test
    void testAddAmenityToHotel() {
        AmenityModel amenityToAdd = new AmenityModel(1, "Tested");
        HotelModel hotelToUpdate = new HotelModel(1, "Hotel with new amenity", "Address", 10);
        hotelToUpdate.getAmenities().add(new AmenityModel(2, "WiFi"));
        assertEquals(hotelToUpdate.getAmenities().size(), 1);
        when(amenityService.getById(1)).thenReturn(amenityToAdd);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelToUpdate));
        when(hotelRepository.save(hotelToUpdate)).thenReturn(hotelToUpdate);
        HotelModel hotelUpdated = hotelService.addAmenityToHotel(1, 1);
        assertEquals(hotelUpdated.getAmenities().size(), 2);
    }

    @Test
    void testAddAmenityToHotelRepeted() {
        AmenityModel amenityToAdd = new AmenityModel(1, "Tested");
        HotelModel hotelToUpdate = new HotelModel(1, "Hotel with new amenity", "Address", 10);
        hotelToUpdate.getAmenities().add(amenityToAdd);
        when(amenityService.getById(1)).thenReturn(amenityToAdd);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelToUpdate));
        when(hotelRepository.save(hotelToUpdate)).thenReturn(hotelToUpdate);
        HotelModel hotelUpdated = hotelService.addAmenityToHotel(1, 1);
        assertEquals(hotelUpdated.getAmenities().size(), 1);
    }

    @Test
    void testCreateHotel() {
        HotelModel hotelToSave = new HotelModel(null, "New Hotel", "Address", 8);
        HotelModel hotelSaved = new HotelModel(1, "New Hotel", "Address", 8);
        when(hotelRepository.save(hotelToSave)).thenReturn(hotelSaved);
        assertEquals(hotelSaved, hotelService.createHotel(hotelToSave));
    }

    @Test
    void testCreateHotelError() {
        HotelModel hotelToSave = new HotelModel(null, "New Hotel", "Address", 8);
        when(hotelRepository.save(hotelToSave)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(ConflictException.class, () -> hotelService.createHotel(hotelToSave));
    }

    @Test
    void testDeleteHotel() {
        HotelModel hotelToDelete = new HotelModel(1, "Hotel to delete", "Address", 5.7);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelToDelete));
        assertEquals(hotelToDelete, hotelService.deleteHotel(1));
    }

    @Test
    void testFilterHotels() {
        PageRequest pagerRequest = PageRequest.of(0, 10);
        Page<HotelModel> page = new PageImpl<>(new ArrayList<>());
        when(hotelRepository.findByNameContaining("", pagerRequest)).thenReturn(page);
        assertNotNull(hotelService.filterHotels("", 0, 10));
    }

    @Test
    void testGetById() {
        HotelModel hotelModel = new HotelModel(1, "Hotel Testing", "Address", 8.5);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelModel));
        HotelModel hotel = hotelService.getById(1);
        assertEquals(hotelModel, hotel);
    }

    @Test
    void testGetByIdNotFound() {
        when(hotelRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> hotelService.getById(1));
    }

    @Test
    void testRemoveAmenityFromHotel() {
        AmenityModel amenityToRemove = new AmenityModel(1, "Tested");
        HotelModel hotelToUpdate = new HotelModel(1, "Hotel with new amenity", "Address", 10);
        hotelToUpdate.getAmenities().add(amenityToRemove);
        hotelToUpdate.getAmenities().add(new AmenityModel(2, "WiFi"));
        assertEquals(hotelToUpdate.getAmenities().size(), 2);
        when(amenityService.getById(1)).thenReturn(amenityToRemove);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelToUpdate));
        when(hotelRepository.save(hotelToUpdate)).thenReturn(hotelToUpdate);
        HotelModel hotelUpdated = hotelService.removeAmenityFromHotel(1, 1);
        assertEquals(hotelUpdated.getAmenities().size(), 1);
    }

    @Test
    void testUpdateHotel() {
        HotelModel hotelUpdated = new HotelModel(1, "New Hotel", "Address", 8);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelUpdated));
        when(hotelRepository.save(hotelUpdated)).thenReturn(hotelUpdated);
        assertEquals(hotelUpdated, hotelService.updateHotel(hotelUpdated));
    }

}
