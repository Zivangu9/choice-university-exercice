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

import choice.university.ivan.mapper.HotelMapper;
import choice.university.ivan.model.AmenityModel;
import choice.university.ivan.model.HotelModel;
import choice.university.ivan.schemas.AddAmenityHotelResponse;
import choice.university.ivan.schemas.CreateHotelResponse;
import choice.university.ivan.schemas.DeleteHotelResponse;
import choice.university.ivan.schemas.FilterHotelsResponse;
import choice.university.ivan.schemas.GetHotelByIdResponse;
import choice.university.ivan.schemas.RemoveAmenityHotelResponse;
import choice.university.ivan.schemas.UpdateHotelResponse;
import choice.university.ivan.service.HotelService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public FilterHotelsResponse filterHotels(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return hotelService.filterHotels(name, page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelModel> getHotelByID(@PathVariable("id") int id) {
        GetHotelByIdResponse getHotelByIdResponse = hotelService.getHotelById(id);
        if (getHotelByIdResponse.getServiceStatus().getStatusCode() == 404) {
            return new ResponseEntity<HotelModel>(HttpStatus.NOT_FOUND);
        }
        HotelModel hotel = HotelMapper.getHotelModel(getHotelByIdResponse.getHotel());
        return new ResponseEntity<HotelModel>(hotel, HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<HotelModel> createHotel(@RequestBody HotelModel hotel) {
        CreateHotelResponse createHotelResponse = hotelService.createHotel(hotel);
        if (createHotelResponse.getServiceStatus().getStatusCode() == 409) {
            return new ResponseEntity<HotelModel>(HttpStatus.CONFLICT);
        }
        HotelModel hotelCreated = HotelMapper.getHotelModel(createHotelResponse.getHotel());
        return new ResponseEntity<HotelModel>(hotelCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelModel> updateHotel(@PathVariable("id") int id, @RequestBody HotelModel hotel) {
        hotel.setId(id);
        UpdateHotelResponse updateHotelResponse = hotelService.updateHotel(hotel);
        if (updateHotelResponse.getServiceStatus().getStatusCode() == 404) {
            return new ResponseEntity<HotelModel>(HttpStatus.NOT_FOUND);
        }
        if (updateHotelResponse.getServiceStatus().getStatusCode() == 409) {
            return new ResponseEntity<HotelModel>(HttpStatus.CONFLICT);
        }
        HotelModel hotelUpdated = HotelMapper.getHotelModel(updateHotelResponse.getHotel());
        return new ResponseEntity<HotelModel>(hotelUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HotelModel> deleteHotelByID(@PathVariable("id") int id) {
        DeleteHotelResponse deleteHotelResponse = hotelService.deleteHotel(id);
        if (deleteHotelResponse.getServiceStatus().getStatusCode() == 404) {
            return new ResponseEntity<HotelModel>(HttpStatus.NOT_FOUND);
        }
        if (deleteHotelResponse.getServiceStatus().getStatusCode() == 409) {
            return new ResponseEntity<HotelModel>(HttpStatus.CONFLICT);
        }
        HotelModel hotelDeleted = HotelMapper.getHotelModel(deleteHotelResponse.getHotel());
        return new ResponseEntity<HotelModel>(hotelDeleted, HttpStatus.OK);
    }

    @GetMapping("/{id}/amenities")
    public ResponseEntity<List<AmenityModel>> getHotelAmenities(@PathVariable("id") int id) {
        GetHotelByIdResponse getHotelResponse = hotelService.getHotelById(id);
        if (getHotelResponse.getServiceStatus().getStatusCode() == 404) {
            return new ResponseEntity<List<AmenityModel>>(HttpStatus.NOT_FOUND);
        }
        HotelModel hotel = HotelMapper.getHotelModel(getHotelResponse.getHotel());
        return new ResponseEntity<List<AmenityModel>>(hotel.getAmenities(), HttpStatus.FOUND);
    }

    @PutMapping("/{hotelId}/amenities/{amenityId}")
    public ResponseEntity<List<AmenityModel>> addHotelAmenityModel(@PathVariable("hotelId") int hotelId,
            @PathVariable("amenityId") int amenityId) {
        AddAmenityHotelResponse addAmenityHotelResponse = hotelService.addAmenityToHotelByIds(hotelId, amenityId);
        if (addAmenityHotelResponse.getServiceStatus().getStatusCode() == 404) {
            return new ResponseEntity<List<AmenityModel>>(HttpStatus.NOT_FOUND);
        }
        if (addAmenityHotelResponse.getServiceStatus().getStatusCode() == 409) {
            return new ResponseEntity<List<AmenityModel>>(HttpStatus.CONFLICT);
        }
        HotelModel hotelUpdated = HotelMapper.getHotelModel(addAmenityHotelResponse.getHotel());
        return new ResponseEntity<List<AmenityModel>>(hotelUpdated.getAmenities(), HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}/amenities/{amenityId}")
    public ResponseEntity<List<AmenityModel>> deleteHotelAmenityModel(@PathVariable("hotelId") int hotelId,
            @PathVariable("amenityId") int amenityId) {
        RemoveAmenityHotelResponse removeAmenityHotelResponse = hotelService.removeAmenityFromHotelByIds(hotelId,
                amenityId);
        if (removeAmenityHotelResponse.getServiceStatus().getStatusCode() == 404) {
            return new ResponseEntity<List<AmenityModel>>(HttpStatus.NOT_FOUND);
        }
        if (removeAmenityHotelResponse.getServiceStatus().getStatusCode() == 409) {
            return new ResponseEntity<List<AmenityModel>>(HttpStatus.CONFLICT);
        }
        HotelModel hotelUpdated = HotelMapper.getHotelModel(removeAmenityHotelResponse.getHotel());
        return new ResponseEntity<List<AmenityModel>>(hotelUpdated.getAmenities(), HttpStatus.OK);
    }
}
