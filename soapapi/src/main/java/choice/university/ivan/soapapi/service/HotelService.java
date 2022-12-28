package choice.university.ivan.soapapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.repository.HotelRepository;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public Optional<HotelModel> getById(int id) {
        return hotelRepository.findById(id);
    }

    public List<HotelModel> getAll() {
        return hotelRepository.findAll();
    }

    public Page<HotelModel> filterHotels(String name, int page, int size) {
        return hotelRepository.findByNameContaining(name, PageRequest.of(page, size));
    }

    public HotelModel createHotel(HotelModel hotel) {
        return hotelRepository.save(hotel);
    }

    public HotelModel deleteHotel(int id) {
        Optional<HotelModel> hotelToDelete = getById(id);
        if (hotelToDelete.isPresent()) {
            hotelRepository.deleteById(id);
            return hotelToDelete.get();
        }
        return null;
    }
}
