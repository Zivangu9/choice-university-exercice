package choice.university.ivan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.GetHotelResponse;
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
    public List<HotelModel> hotels() {
        List<HotelModel> list = new ArrayList<>();
        list.add(new HotelModel(0, "Círculo Mexicano", "Mexico City Historic Centre, Mexico City"));
        return list;
    }

    @GetMapping("/{id}")
    public HotelModel getHotelByID(@PathVariable("id") int id) {
        GetHotelResponse getHotelResponse = hotelService.getHotelById(id);
        HotelModel hotel = HotelMapper.getHotelModel(getHotelResponse.getHotel());
        return hotel;
    }

    @PostMapping("")
    public HotelModel createHotel(@RequestBody HotelModel hotel) {
        return hotel;
    }

    @PutMapping("/{id}")
    public HotelModel updateHotel(@PathVariable("id") int id, @RequestBody HotelModel hotel) {
        hotel.setId(id);
        return hotel;
    }

    @DeleteMapping("/{id}")
    public HotelModel deleteHotelByID(@PathVariable("id") int id) {
        return new HotelModel(id, "Hotel Deleted", "");
    }

    @GetMapping("/{id}/amenities")
    public List<AmenityModel> getHotelAmenities(@PathVariable("id") int id) {
        List<AmenityModel> list = new ArrayList<>();
        list.add(new AmenityModel(1, "Wifi"));
        list.add(new AmenityModel(2, "Pool"));
        return list;
    }

    @PutMapping("/{hotelId}/amenities/{amenityId}")
    public HotelModel addHotelAmenityModel(@PathVariable("hotelId") int hotelId, @PathVariable("amenityId") int amenityId) {
        List<AmenityModel> hotelAmenities = getHotelAmenities(hotelId);
        hotelAmenities.add(new AmenityModel(amenityId, "Restaurant"));
        HotelModel hotel = getHotelByID(hotelId);
        hotel.setAmenities(hotelAmenities);
        return hotel;
    }

    @DeleteMapping("/{hotelId}/amenities/{amenityId}")
    public HotelModel deleteHotelAmenityModel(@PathVariable("hotelId") int hotelId, @PathVariable("amenityId") int amenityId) {
        List<AmenityModel> hotelAmenities = getHotelAmenities(hotelId);
        hotelAmenities = hotelAmenities.stream().filter(amenity -> amenity.getId() != amenityId)
                .collect(Collectors.toList());
        HotelModel hotel = getHotelByID(hotelId);
        hotel.setAmenities(hotelAmenities);
        return hotel;
    }
}
