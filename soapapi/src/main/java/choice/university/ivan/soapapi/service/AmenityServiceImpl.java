package choice.university.ivan.soapapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import choice.university.ivan.soapapi.repository.AmenityRepository;

@Service
public class AmenityServiceImpl implements AmenityService {
    @Autowired
    private AmenityRepository amenityRepository;

    public Boolean amenityWithIdExists(int id) {
        return amenityRepository.findById(id).isPresent();
    }
}
