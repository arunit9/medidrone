package com.app.medidrone.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.medidrone.dao.entity.MedicationProduct;

@Repository
public interface MedicationProductJpaRepository extends JpaRepository<MedicationProduct, String>{

	Boolean existsByCode(String code);
}
