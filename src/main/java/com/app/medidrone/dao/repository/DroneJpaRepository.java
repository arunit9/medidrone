package com.app.medidrone.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.medidrone.dao.entity.Drone;
import com.app.medidrone.dao.entity.State;

@Repository
public interface DroneJpaRepository extends JpaRepository<Drone, String>{

	List<Drone> findByStateIn(List<State> states);

	Boolean existsBySerialNumber(String serialNumber);

}
