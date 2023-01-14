package choice.university.ivan.soapapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import choice.university.ivan.schemas.Amenity;
import choice.university.ivan.soapapi.model.AmenityModel;

@Mapper
public interface AmenityMapper {
    AmenityMapper INSTANCE = Mappers.getMapper(AmenityMapper.class);

    Amenity amenityModelToAmenity(AmenityModel amenityModel);

    void updateListAmenities(@MappingTarget List<Amenity> hotels, List<AmenityModel> hotelModels);
}
