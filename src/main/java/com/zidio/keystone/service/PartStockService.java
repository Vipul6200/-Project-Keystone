package com.zidio.keystone.service;
import com.zidio.keystone.entity.PartStock;
import com.zidio.keystone.repository.PartStockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PartStockService {
    private final PartStockRepository partStockRepository;
    public PartStockService(PartStockRepository partStockRepository) {
        this.partStockRepository = partStockRepository;
    }
    public PartStock addStock(PartStock partStock) {
        return partStockRepository.save(partStock);
    }
    public boolean reduceStock(String partName, Integer quantity) {
        Optional<PartStock> existing = partStockRepository.findByPartName(partName);
        if (existing.isEmpty()) {
            return false;
        }
        PartStock stock = existing.get();
        if (stock.getStockQuantity() < quantity) {
            return false;
        }

        stock.setStockQuantity(stock.getStockQuantity() - quantity);
        partStockRepository.save(stock);
        return true;
    }
    public java.util.List<PartStock> getAllStock() {
        return partStockRepository.findAll();
    }
    
}
