package choice.university.ivan.soapapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.repository.HotelRepository;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelModel> getAll() {
        return hotelRepository.findAll();
    }

    public Optional<HotelModel> getById(int id) {
        return hotelRepository.findById(id);
    }
}
