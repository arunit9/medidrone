package com.app.medidrone.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.medidrone.dao.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	Boolean existsByUsername(String username);

}
