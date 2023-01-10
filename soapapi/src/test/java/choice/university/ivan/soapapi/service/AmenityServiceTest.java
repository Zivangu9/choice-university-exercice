package choice.university.ivan.soapapi.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import choice.university.ivan.soapapi.exception.NotFoundException;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.repository.AmenityRepository;

@ExtendWith(SpringExtension.class)
public class AmenityServiceTest {
    @Mock
    private AmenityRepository amenityRepository;
    @InjectMocks
    private AmenityServiceImpl underTest;

    @Test
    void testGetById() {
        AmenityModel amenityModel = new AmenityModel(1, "Internet");
        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenityModel));
        assertNotNull(underTest.getById(1));
    }

    @Test
    void testGetByIdNotFound() {
        when(amenityRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->underTest.getById(1));
    }

    @Test
    void testGetAll() {
        when(amenityRepository.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(underTest.getAll());
    }
}
