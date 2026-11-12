package com.zidio.keystone.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkOrderPartDTO {
    @NotBlank(message = "Part name is required")
    private String partName;
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    public WorkOrderPartDTO() {
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
