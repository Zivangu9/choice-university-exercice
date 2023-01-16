package choice.university.ivan.soapapi.service;

import java.util.List;

import choice.university.ivan.soapapi.model.AmenityModel;

public interface AmenityService {
    AmenityModel getById(int id);

    List<AmenityModel> getAll();
}
