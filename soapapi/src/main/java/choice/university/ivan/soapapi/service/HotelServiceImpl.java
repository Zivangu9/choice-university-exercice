package choice.university.ivan.soapapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.repository.AmenityRepository;
import choice.university.ivan.soapapi.repository.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AmenityRepository amenityRepository;

    public Optional<HotelModel> getById(int id) {
        return hotelRepository.findById(id);
    }

    public Boolean hotelWithIdExists(int id) {
        return hotelRepository.findById(id).isPresent();
    }

    public Page<HotelModel> filterHotels(String name, int page, int size) {
        return hotelRepository.findByNameContaining(name, PageRequest.of(page, size));
    }

    public HotelModel createHotel(HotelModel hotel) {
        HotelModel hotelCreated = null;
        try {
            hotelCreated = hotelRepository.save(hotel);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
        return hotelCreated;
    }

    public HotelModel updateHotel(HotelModel hotel) {
        Optional<HotelModel> hotelToUpdate = getById(hotel.getId());
        if (hotelToUpdate.isPresent()) {
            HotelModel hotelUpdated = hotelToUpdate.get();
            hotelUpdated.setName(hotel.getName());
            hotelUpdated.setAddress(hotel.getAddress());
            hotelUpdated.setRating(hotel.getRating());
            return hotelRepository.save(hotelUpdated);
        }
        return null;
    }

    public HotelModel deleteHotel(int id) {
        Optional<HotelModel> hotelToDelete = getById(id);
        if (hotelToDelete.isPresent()) {
            hotelRepository.deleteById(id);
            return hotelToDelete.get();
        }
        return null;
    }

    public HotelModel addAmenityToHotel(int idHotel, int idAmenity) {
        Optional<HotelModel> hotelToUpdate = getById(idHotel);
        Optional<AmenityModel> amenityToAdd = amenityRepository.findById(idAmenity);
        if (hotelToUpdate.isPresent() && amenityToAdd.isPresent()) {
            HotelModel hotelUpdated = hotelToUpdate.get();
            AmenityModel amenity = amenityToAdd.get();
            List<AmenityModel> amenities = hotelUpdated.getAmenities();
            boolean isAmenityInList = false;
            for (AmenityModel amenityModel : amenities)
                if (amenityModel.getId() == idAmenity)
                    isAmenityInList = true;
            if (!isAmenityInList) {
                amenities.add(amenity);
            }
            return hotelRepository.save(hotelUpdated);
        }
        return null;
    }

    public HotelModel removeAmenityFromHotel(int idHotel, int idAmenity) {
        Optional<HotelModel> hotelToUpdate = getById(idHotel);
        if (hotelToUpdate.isPresent()) {
            HotelModel hotelUpdated = hotelToUpdate.get();
            List<AmenityModel> amenities = hotelUpdated.getAmenities();
            AmenityModel amenityToRemove = null;
            for (AmenityModel amenityModel : amenities)
                if (amenityModel.getId() == idAmenity)
                    amenityToRemove = amenityModel;
            amenities.remove(amenityToRemove);
            return hotelRepository.save(hotelUpdated);
        }
        return null;
    }
}