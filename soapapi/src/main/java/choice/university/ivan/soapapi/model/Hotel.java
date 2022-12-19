package choice.university.ivan.soapapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String address;
    private double rating;

    @ManyToMany
    private List<Amenity> amenities;
}
