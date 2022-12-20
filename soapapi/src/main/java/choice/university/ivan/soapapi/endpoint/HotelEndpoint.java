package choice.university.ivan.soapapi.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.schemas.GetHotelRequest;
import choice.university.ivan.schemas.GetHotelResponse;
import choice.university.ivan.soapapi.model.Hotel;
import choice.university.ivan.soapapi.service.HotelService;

@Endpoint
public class HotelEndpoint {
    @Autowired
    private HotelService hotelService;

    @PayloadRoot(namespace = "http://localhost:8081/hotels", localPart = "GetHotelRequest")
    @ResponsePayload
    public GetHotelResponse processHotelRequest(@RequestPayload GetHotelRequest request) {
        GetHotelResponse response = new GetHotelResponse();
        response.setHotel(null);
        Optional<Hotel> optionalHotel = hotelService.getById(request.getId());
        if (optionalHotel.isPresent()) {
            Hotel myHotel = optionalHotel.get();
            choice.university.ivan.schemas.Hotel hotel = new choice.university.ivan.schemas.Hotel();
            hotel.setId(myHotel.getId());
            hotel.setName(myHotel.getName());
            hotel.setAddress(myHotel.getAddress());
            hotel.setRating(myHotel.getRating());
            List<Amenity> amenities = hotel.getAmenities();
            for (choice.university.ivan.soapapi.model.Amenity amenity : myHotel.getAmenities()) {
                Amenity a = new Amenity();
                a.setId(amenity.getId());
                a.setName(amenity.getName());
                amenities.add(a);
            }
            response.setHotel(hotel);
        }
        return response;
    }
}
