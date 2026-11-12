package com.zidio.keystone.service;
import com.zidio.keystone.entity.Site;
import com.zidio.keystone.repository.SiteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service 
public class SiteService {
    private final SiteRepository siteRepository;
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
    public Optional<Site> getSiteById(Long id) {
        return siteRepository.findById(id);
    }
    public Site createSite(Site site) {
        return siteRepository.save(site);
    }
    public Site updateSite(Long id, Site siteDetails) {
        Optional<Site> optionalSite = siteRepository.findById(id);
        if (optionalSite.isPresent()) {
            Site updated = optionalSite.get();
            updated.setSiteName(siteDetails.getSiteName());
            updated.setLocation(siteDetails.getLocation());
            updated.setCustomer(siteDetails.getCustomer());
            return siteRepository.save(updated);
        }
        return null;
    }
    public void deleteSite(Long id) {
        siteRepository.deleteById(id);
    }
}