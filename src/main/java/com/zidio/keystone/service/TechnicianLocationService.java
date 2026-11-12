package com.zidio.keystone.service;
import com.zidio.keystone.dto.TechnicianLocationRequestDTO;
import com.zidio.keystone.entity.TechnicianLocation;
import com.zidio.keystone.entity.User;
import com.zidio.keystone.repository.TechnicianLocationRepository;
import com.zidio.keystone.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
@Service
public class TechnicianLocationService {
    private final TechnicianLocationRepository locationRepository;
    private final UserRepository userRepository;

    public TechnicianLocationService(
            TechnicianLocationRepository locationRepository,
            UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TechnicianLocation updateMyLocation(
            String username,
            TechnicianLocationRequestDTO request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        if (!"TECHNICIAN".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only technicians can update their location");
        }

        if (request.getLatitude() == null
                || request.getLatitude() < -90
                || request.getLatitude() > 90) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid latitude");
        }

        if (request.getLongitude() == null
                || request.getLongitude() < -180
                || request.getLongitude() > 180) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid longitude");
        }

        TechnicianLocation location = locationRepository
                .findByTechnicianId(user.getId())
                .orElseGet(TechnicianLocation::new);

        location.setTechnicianId(user.getId());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setUpdatedAt(LocalDateTime.now());

        return locationRepository.save(location);
    }
}
