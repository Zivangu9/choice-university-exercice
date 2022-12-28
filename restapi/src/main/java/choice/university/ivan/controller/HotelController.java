package choice.university.ivan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.GetHotelResponse;
import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.schemas.Pagination;
import choice.university.ivan.service.HotelService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @GetMapping
    public Pagination hotels(@RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return hotelService.filterHotels(name, page);
    }

    @GetMapping("/{id}")
    public HotelModel getHotelByID(@PathVariable("id") int id) {
        GetHotelResponse getHotelResponse = hotelService.getHotelById(id);
        HotelModel hotel = HotelMapper.getHotelModel(getHotelResponse.getHotel());
        return hotel;
    }

    @PostMapping("")
    public HotelModel createHotel(@RequestBody HotelModel hotel) {
        Hotel hotelCreated = hotelService.createHotel(hotel).getHotel();
        return HotelMapper.getHotelModel(hotelCreated);
    }

    @PutMapping("/{id}")
    public HotelModel updateHotel(@PathVariable("id") int id, @RequestBody HotelModel hotel) {
        hotel.setId(id);
        hotelService.updateHotel(hotel);
        return HotelMapper.getHotelModel(hotelService.updateHotel(hotel).getHotel());
    }

    @DeleteMapping("/{id}")
    public HotelModel deleteHotelByID(@PathVariable("id") int id) {
        return HotelMapper.getHotelModel(hotelService.deleteHotel(id).getHotel());
    }

    @GetMapping("/{id}/amenities")
    public List<AmenityModel> getHotelAmenities(@PathVariable("id") int id) {
        return HotelMapper.getAmenities(hotelService.getAmenitiesByHotelId(id));
    }

    @PutMapping("/{hotelId}/amenities/{amenityId}")
    public List<AmenityModel> addHotelAmenityModel(@PathVariable("hotelId") int hotelId,
            @PathVariable("amenityId") int amenityId) {
        return HotelMapper.getAmenities(hotelService.addAmenityToHotelByIds(hotelId, amenityId));
    }

    @DeleteMapping("/{hotelId}/amenities/{amenityId}")
    public List<AmenityModel> deleteHotelAmenityModel(@PathVariable("hotelId") int hotelId,
            @PathVariable("amenityId") int amenityId) {
        return HotelMapper.getAmenities(hotelService.removeAmenityFromHotelByIds(hotelId, amenityId));
    }
}
