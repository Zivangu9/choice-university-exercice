package choice.university.ivan.soapapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import choice.university.ivan.soapapi.exception.NotFoundException;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.repository.AmenityRepository;

@Service
public class AmenityServiceImpl implements AmenityService {
    @Autowired
    private AmenityRepository amenityRepository;

    public AmenityModel getById(int id) {
        Optional<AmenityModel> optional = amenityRepository.findById(id);
        if (!optional.isPresent())
            throw new NotFoundException("Amenity with id: " + id + " not found");
        return optional.get();
    }

    public List<AmenityModel> getAll() {
        return amenityRepository.findAll();
    }
}
