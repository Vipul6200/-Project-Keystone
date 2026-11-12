package com.zidio.keystone.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "technician_locations")
public class TechnicianLocation {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, unique = true)
    private Long technicianId;
    @Column (nullable = false)
    private Double latitude;
    @Column (nullable = false)
    private Double longitude;

    private LocalDateTime updatedAt;
    public TechnicianLocation() {

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTechnicianId() {
        return technicianId;
    }
    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
