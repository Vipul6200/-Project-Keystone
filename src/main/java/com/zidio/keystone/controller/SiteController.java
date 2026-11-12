package com.zidio.keystone.controller;
import com.zidio.keystone.dto.SiteDTO;
import com.zidio.keystone.repository.CustomerRepository;
import com.zidio.keystone.entity.Customer;
import com.zidio.keystone.entity.Site;
import com.zidio.keystone.service.SiteService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@RestController 
@RequestMapping("/api/sites")
public class SiteController {
 private final SiteService siteService;
 private final CustomerRepository customerRepository;
 public SiteController(SiteService siteService, CustomerRepository customerRepository) {
    this.siteService = siteService;
    this.customerRepository = customerRepository;
  }
  @GetMapping
    public List<Site> getAllSites() {
     return siteService.getAllSites();
  }   
  @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable Long id) {
        return siteService.getSiteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
   @PostMapping
     public ResponseEntity<Site> createSite(@Valid @RequestBody SiteDTO siteDTO) {
        Site site = new Site();
        site.setSiteName(siteDTO.getSiteName());
        site.setLocation(siteDTO.getLocation());
        if (siteDTO.getCustomerId() != null) {
             Customer customer = customerRepository.findById(siteDTO.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));
              site.setCustomer(customer);
        }
        return ResponseEntity.ok(siteService.createSite(site));
    }
    @PutMapping("/{id}")
        public ResponseEntity<Site> updateSite(
        @PathVariable Long id,
        @Valid @RequestBody SiteDTO siteDTO) {

          Site site = new Site();
          site.setSiteName(siteDTO.getSiteName());
          site.setLocation(siteDTO.getLocation());

      if (siteDTO.getCustomerId() != null) {
        Customer customer = customerRepository.findById(siteDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        site.setCustomer(customer);
      }

       return ResponseEntity.ok(siteService.updateSite(id, site));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }

}