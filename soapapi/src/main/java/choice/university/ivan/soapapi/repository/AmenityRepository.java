package choice.university.ivan.soapapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import choice.university.ivan.soapapi.model.AmenityModel;

public interface AmenityRepository extends JpaRepository<AmenityModel, Integer> {
}
