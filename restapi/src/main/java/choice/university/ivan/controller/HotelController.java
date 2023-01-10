package choice.university.ivan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.service.HotelServiceImpl;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping
    public FilterHotelsResponse filterHotels(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return hotelService.filterHotels(name, page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelModel> getHotelByID(@PathVariable("id") int id) {
        return new ResponseEntity<HotelModel>(hotelService.getHotelById(id), HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<HotelModel> createHotel(@RequestBody HotelModel hotel) {
        return new ResponseEntity<HotelModel>(hotelService.createHotel(hotel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelModel> updateHotel(@PathVariable("id") int id, @RequestBody HotelModel hotel) {
        hotel.setId(id);
        return new ResponseEntity<HotelModel>(hotelService.updateHotel(hotel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HotelModel> deleteHotelByID(@PathVariable("id") int id) {
        return new ResponseEntity<HotelModel>(hotelService.deleteHotel(id), HttpStatus.OK);
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<AmenityModel>> getAllAmenities() {
        return new ResponseEntity<List<AmenityModel>>(hotelService.getAllAmenities(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}/amenities")
    public ResponseEntity<List<AmenityModel>> getHotelAmenities(@PathVariable("id") int id) {
        return new ResponseEntity<List<AmenityModel>>(hotelService.getHotelById(id).getAmenities(), HttpStatus.FOUND);
    }

    @PutMapping("/{hotelId}/amenities/{amenityId}")
    public ResponseEntity<List<AmenityModel>> addHotelAmenityByIds(@PathVariable("hotelId") int hotelId,
            @PathVariable("amenityId") int amenityId) {
        return new ResponseEntity<List<AmenityModel>>(hotelService.addAmenityToHotelByIds(hotelId, amenityId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}/amenities/{amenityId}")
    public ResponseEntity<List<AmenityModel>> removeHotelAmenityByIds(@PathVariable("hotelId") int hotelId,
            @PathVariable("amenityId") int amenityId) {
        return new ResponseEntity<List<AmenityModel>>(hotelService.removeAmenityFromHotelByIds(hotelId,
                amenityId), HttpStatus.OK);
    }
}
