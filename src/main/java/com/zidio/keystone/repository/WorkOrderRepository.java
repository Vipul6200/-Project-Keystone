package com.zidio.keystone.repository;

import com.zidio.keystone.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
 List<WorkOrder> findByTitleContainingIgnoreCase(String title); 
 List<WorkOrder> findByStatusIgnoreCase(String status);
 List<WorkOrder> findByAssignedTo(Long assignedTo);
 List<WorkOrder> findBySiteCustomerId(Long customerId);
 Page<WorkOrder> findByAssignedTo(Long assignedTo, Pageable pageable);
}
