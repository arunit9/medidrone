package com.app.medidrone.dao.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString(exclude = {"medications"})
public class Drone implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
	@Column(nullable=false)
	private String serialNumber;

    @Enumerated(EnumType.STRING)
	private Model model;

	private Integer weightLimit;

	private Integer batteryCapacity;

	@Enumerated(EnumType.STRING)
	private State state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(	name = "drone_medicine", 
				joinColumns = @JoinColumn(name = "serialNumber"), 
				inverseJoinColumns = @JoinColumn(name = "code"))
	private Set<Medication> medications = new HashSet<>();

}
