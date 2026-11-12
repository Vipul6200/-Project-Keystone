package com.zidio.keystone.repository;
import com.zidio.keystone.entity.WorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistory, Long> {

    List<WorkOrderHistory> findByWorkOrderId(Long workOrderId);
}
