package com.zidio.keystone.entity;
import jakarta.persistence.*;

@Entity 
@Table(name = "work_order_parts")
public class WorkOrderPart {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_order_id")
    private Long workOrderId;
    @Column (name = "part_name")
    private String partName;

    private Integer quantity;

    public WorkOrderPart() {

    }
    public Long getId() {
        return id;
    }
    public Long getWorkOrderId() {
        return workOrderId;
    }
    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }
    public String getPartName() {
        return partName;
    }
    public void setPartName(String partName) {
        this.partName = partName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
