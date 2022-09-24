package com.app.medidrone.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.medidrone.dao.entity.Role;
import com.app.medidrone.dao.entity.RoleType;

public interface RoleJpaRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(RoleType name);
}
