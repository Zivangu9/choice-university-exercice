package choice.university.ivan.soapapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import choice.university.ivan.soapapi.model.Hotel;
import choice.university.ivan.soapapi.repository.HotelRepository;

public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }
}
