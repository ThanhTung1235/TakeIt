package com.takeIt.repository;

import com.takeIt.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<List<District>> findByCity_Id(long id);
}
