package com.app.medidrone.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.medidrone.dao.entity.AuditHistory;

@Repository
public interface AuditHistoryJpaRepository extends JpaRepository<AuditHistory, Long>{

}
