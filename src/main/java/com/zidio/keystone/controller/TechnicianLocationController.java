package com.zidio.keystone.controller;
import com.zidio.keystone.dto.TechnicianLocationRequestDTO;
import com.zidio.keystone.entity.TechnicianLocation;
import com.zidio.keystone.service.TechnicianLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping ("/api/technician/location")
public class TechnicianLocationController {
    private final TechnicianLocationService locationService;
    public TechnicianLocationController(TechnicianLocationService locationService) {
        this.locationService = locationService;
    }
    @PutMapping("/my-location")
    public ResponseEntity<TechnicianLocation> updateMyLocation(
        Authentication authentication,
        @RequestBody TechnicianLocationRequestDTO request) {
            TechnicianLocation updatedLocation = locationService.updateMyLocation(
                authentication.getName(), request
            );
            return ResponseEntity.ok(updatedLocation);
        }
    
}
