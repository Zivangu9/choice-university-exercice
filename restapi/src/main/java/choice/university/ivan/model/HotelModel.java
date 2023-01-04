package choice.university.ivan.model;

import java.util.List;

public class HotelModel {
    private int id;
    private String name;
    private String address;
    private double rating;
    private List<AmenityModel> amenities;

    public HotelModel() {
    }

    public HotelModel(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<AmenityModel> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<AmenityModel> amenities) {
        this.amenities = amenities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        HotelModel hotelModel = (HotelModel) obj;
        if (hotelModel.getId() != this.getId())
            return false;
        if (!hotelModel.getName().equals(this.getName()))
            return false;
        if (!hotelModel.getAddress().equals(this.getAddress()))
            return false;
        if (hotelModel.getRating() != this.getRating())
            return false;
        if (!hotelModel.getAmenities().equals(this.getAmenities()))
            return false;
        return true;
    }

}
