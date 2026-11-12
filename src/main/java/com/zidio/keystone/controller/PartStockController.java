package com.zidio.keystone.controller;
import com.zidio.keystone.entity.PartStock;
import com.zidio.keystone.service.PartStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/part-stock")
public class PartStockController {

    private final PartStockService partStockService;

    public PartStockController(PartStockService partStockService) {
        this.partStockService = partStockService;
    }
    
    @PostMapping
    public PartStock addStock(@RequestBody PartStock partStock) {
        return partStockService.addStock(partStock);
    }

    @GetMapping
    public List<PartStock> getAllStock() {
        return partStockService.getAllStock();
    }
    
}