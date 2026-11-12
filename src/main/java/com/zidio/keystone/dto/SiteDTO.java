package com.zidio.keystone.dto;
import jakarta.validation.constraints.NotBlank;

public class SiteDTO {
 @NotBlank(message = "Site name is required")
 private String siteName;
 @NotBlank(message = "Location is required")    
 private String location;
 private Long customerId;
 public SiteDTO() {

 }
 public SiteDTO(String siteName, String location, Long customerId) {
     this.siteName = siteName;
     this.location = location;
     this.customerId = customerId;
    }
  public String getSiteName() {
    return siteName;
   }
   public void setSiteName(String siteName) {
    this.siteName = siteName;
   }
   public String getLocation() {
    return location;
   }
   public void setLocation(String location) {
    this.location = location;
   }
   public Long getCustomerId() {
    return customerId;
   }
   public void setCustomerId(Long customerId) {
    this.customerId = customerId;
   }
   
}
