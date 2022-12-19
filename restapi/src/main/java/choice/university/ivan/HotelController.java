package choice.university.ivan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @GetMapping
    public List<Hotel> hotels() {
        List<Hotel> list = new ArrayList<>();
        list.add(new Hotel(0, "Círculo Mexicano", "Mexico City Historic Centre, Mexico City"));
        return list;
    }

    @GetMapping("/{id}")
    public Hotel getHotelByID(@PathVariable("id") int id) {
        Hotel hotel = new Hotel(id, "Círculo Mexicano", "Mexico City Historic Centre, Mexico City");
        hotel.setAmenities(getHotelAmenities(id));
        return hotel;
    }

    @PostMapping("")
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotel;
    }

    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable("id") int id, @RequestBody Hotel hotel) {
        hotel.setId(id);
        return hotel;
    }

    @DeleteMapping("/{id}")
    public Hotel deleteHotelByID(@PathVariable("id") int id) {
        return new Hotel(id, "Hotel Deleted", "");
    }

    @GetMapping("/{id}/amenities")
    public List<Amenity> getHotelAmenities(@PathVariable("id") int id) {
        List<Amenity> list = new ArrayList<>();
        list.add(new Amenity(1, "Wifi"));
        list.add(new Amenity(2, "Pool"));
        return list;
    }

    @PutMapping("/{hotelId}/amenities/{amenityId}")
    public Hotel addHotelAmenity(@PathVariable("hotelId") int hotelId, @PathVariable("amenityId") int amenityId) {
        List<Amenity> hotelAmenities = getHotelAmenities(hotelId);
        hotelAmenities.add(new Amenity(amenityId, "Restaurant"));
        Hotel hotel = getHotelByID(hotelId);
        hotel.setAmenities(hotelAmenities);
        return hotel;
    }

    @DeleteMapping("/{hotelId}/amenities/{amenityId}")
    public Hotel deleteHotelAmenity(@PathVariable("hotelId") int hotelId, @PathVariable("amenityId") int amenityId) {
        List<Amenity> hotelAmenities = getHotelAmenities(hotelId);
        hotelAmenities = hotelAmenities.stream().filter(amenity -> amenity.id != amenityId)
                .collect(Collectors.toList());
        Hotel hotel = getHotelByID(hotelId);
        hotel.setAmenities(hotelAmenities);
        return hotel;
    }
}
