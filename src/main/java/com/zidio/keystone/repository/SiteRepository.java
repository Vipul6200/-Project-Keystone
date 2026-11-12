package com.zidio.keystone.repository;
import com.zidio.keystone.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
    
}
