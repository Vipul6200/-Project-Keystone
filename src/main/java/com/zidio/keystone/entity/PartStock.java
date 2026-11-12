package com.zidio.keystone.entity;
import jakarta.persistence.*;

@Entity 
@Table(name = "part_stock")
public class PartStock {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_name")
    private String partName;
    @Column (name = "stock_quantity")
    private Integer stockQuantity;

    public PartStock() {

    }
    public Long getId() {
        return id; 
    }
    public String getPartName() {
        return partName;
    }
    public void setPartName(String partName) {
        this.partName = partName;
    }
    public Integer getStockQuantity() {
        return  stockQuantity;
    }
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
