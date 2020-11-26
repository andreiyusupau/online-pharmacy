package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcurementRepository extends JpaRepository<Procurement,Long> {
    List<Procurement> findByOwner_Id(long id);
}
