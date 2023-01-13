package choice.university.ivan.soapapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import choice.university.ivan.soapapi.exception.BadRequestException;
import choice.university.ivan.soapapi.exception.ConflictException;
import choice.university.ivan.soapapi.exception.NotFoundException;
import choice.university.ivan.soapapi.model.AmenityModel;
import choice.university.ivan.soapapi.model.HotelModel;
import choice.university.ivan.soapapi.repository.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AmenityService amenityService;

    public HotelModel getById(int id) {
        Optional<HotelModel> optional = hotelRepository.findById(id);
        if (!optional.isPresent())
            throw new NotFoundException("Hotel with id: " + id + " not found");
        return optional.get();
    }

    public Page<HotelModel> filterHotels(String name, int page, int size) {
        return hotelRepository.findByNameContaining(name, PageRequest.of(page, size));
    }

    public HotelModel createHotel(HotelModel hotel) {
        validateHotel(hotel);
        try {
            HotelModel hotelCreated = hotelRepository.save(hotel);
            return hotelCreated;
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Exception while adding Hotel");
        }
    }

    public HotelModel updateHotel(HotelModel hotel) {
        validateHotel(hotel);
        HotelModel hotelToUpdate = getById(hotel.getId());
        hotelToUpdate.setName(hotel.getName());
        hotelToUpdate.setAddress(hotel.getAddress());
        hotelToUpdate.setRating(hotel.getRating());
        HotelModel hotelUpdated = hotelRepository.save(hotel);
        return hotelUpdated;
    }

    public HotelModel deleteHotel(int id) {
        HotelModel hotelToDelete = getById(id);
        hotelRepository.deleteById(id);
        return hotelToDelete;
    }

    public HotelModel addAmenityToHotel(int idHotel, int idAmenity) {
        HotelModel hotelToUpdate = getById(idHotel);
        AmenityModel amenityToAdd = amenityService.getById(idAmenity);
        List<AmenityModel> amenities = hotelToUpdate.getAmenities();
        boolean isAmenityInList = false;
        for (AmenityModel amenityModel : amenities)
            if (amenityModel.getId() == idAmenity)
                isAmenityInList = true;
        if (!isAmenityInList) {
            amenities.add(amenityToAdd);
        }
        HotelModel hotelUpdated = hotelRepository.save(hotelToUpdate);
        return hotelUpdated;
    }

    public HotelModel removeAmenityFromHotel(int idHotel, int idAmenity) {
        HotelModel hotelToUpdate = getById(idHotel);
        List<AmenityModel> amenities = hotelToUpdate.getAmenities();
        AmenityModel amenityToRemove = null;
        for (AmenityModel amenityModel : amenities)
            if (amenityModel.getId() == idAmenity)
                amenityToRemove = amenityModel;
        amenities.remove(amenityToRemove);
        HotelModel hotelUpdated = hotelRepository.save(hotelToUpdate);
        return hotelUpdated;
    }

    public void validateHotel(HotelModel hotel) {
        if (hotel.getName() == null || hotel.getName().equals(""))
            throw new BadRequestException("Name can't be empty");

        if (hotel.getAddress() == null || hotel.getAddress().equals(""))
            throw new BadRequestException("Address can't be empty");

        if (hotel.getRating() < 0 || hotel.getRating() > 10)
            throw new BadRequestException("Invalid rating. The rating must be a numeric value between 0 and 10");
    }
}
