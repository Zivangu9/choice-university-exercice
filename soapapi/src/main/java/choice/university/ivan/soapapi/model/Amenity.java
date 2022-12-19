package choice.university.ivan.soapapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}
