package choice.university.ivan.soapapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "hotels")
public class HotelModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String address;
    private double rating;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AmenityModel> amenities = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }

    public List<AmenityModel> getAmenities() {
        return amenities;
    }

}
