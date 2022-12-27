package choice.university.ivan.soapapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import choice.university.ivan.soapapi.model.HotelModel;

public interface HotelRepository extends JpaRepository<HotelModel, Integer> {
    Page<HotelModel> findByNameContaining(String name, Pageable pageable);

}
