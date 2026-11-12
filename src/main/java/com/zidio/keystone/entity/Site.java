package com.zidio.keystone.entity;
import jakarta.persistence.*;

@Entity 
@Table(name = "site")
public class Site {
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "site_name")
    private String siteName;
    private String location;

    @ManyToOne 
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Site() {
    }
    public Site(Long id, String siteName, String location, Customer customer) {
        this.id = id;
        this.siteName = siteName;
        this.location = location;
        this.customer = customer;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
