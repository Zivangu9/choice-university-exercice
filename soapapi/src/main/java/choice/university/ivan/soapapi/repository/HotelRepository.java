package choice.university.ivan.soapapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import choice.university.ivan.soapapi.model.HotelModel;

public interface HotelRepository extends JpaRepository<HotelModel, Integer> {

}
