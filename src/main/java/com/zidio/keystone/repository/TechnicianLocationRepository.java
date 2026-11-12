package com.zidio.keystone.repository;
import com.zidio.keystone.entity.TechnicianLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianLocationRepository extends JpaRepository<TechnicianLocation, Long> {
    Optional<TechnicianLocation> findByTechnicianId(Long technicianId);
}
