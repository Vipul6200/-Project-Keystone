package com.zidio.keystone.repository;
import com.zidio.keystone.entity.PartStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PartStockRepository extends JpaRepository<PartStock, Long> {
    Optional<PartStock> findByPartName(String partName);
}