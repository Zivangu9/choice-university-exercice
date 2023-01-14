package choice.university.ivan.soapapi.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import choice.university.ivan.schemas.Hotel;
import choice.university.ivan.soapapi.model.HotelModel;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, uses = AmenityMapper.class)
public interface HotelMapper {
    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);

    Hotel hotelModelToHotel(HotelModel hotelModel);

    void updateListHotels(@MappingTarget List<Hotel> hotels, List<HotelModel> hotelModels);
}
