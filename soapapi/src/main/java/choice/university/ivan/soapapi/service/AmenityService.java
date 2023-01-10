package choice.university.ivan.soapapi.service;

import java.util.List;

import choice.university.ivan.soapapi.model.AmenityModel;

public interface AmenityService {
    public AmenityModel getById(int id);

    public List<AmenityModel> getAll();
}
