package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.ProcurementPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcurementPositionRepository extends JpaRepository<ProcurementPosition,Long> {
    List<ProcurementPosition> findByProcurement_Id(long id);
    boolean deleteByProcurement_Id(long id);
}
