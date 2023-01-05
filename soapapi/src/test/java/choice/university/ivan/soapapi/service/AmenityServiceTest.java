package choice.university.ivan.soapapi.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.repository.AmenityRepository;

@ExtendWith(SpringExtension.class)
public class AmenityServiceTest {
    @Mock
    private AmenityRepository amenityRepository;
    @InjectMocks
    private AmenityService amenityService;

    @Test
    void testAmenityWithIdExists() {
        AmenityModel amenityModel = new AmenityModel(1, "Internet");
        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenityModel));
        assertTrue(amenityService.amenityWithIdExists(1));
    }
}
