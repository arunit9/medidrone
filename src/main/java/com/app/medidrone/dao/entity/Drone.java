package com.app.medidrone.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "drone")
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
	private List<Medication> medications = new ArrayList<>();

	public Drone(String serialNumber, Model model, Integer weightLimit, Integer batteryCapacity, State state,
			List<Medication> medications) {
		super();
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
		this.medications = medications;
	}

	public Drone(String serialNumber, Model model, Integer weightLimit, Integer batteryCapacity, State state) {
		super();
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
	}

	public String getBatteryAuditInfo() {
		return String.format("drone %s at battery capacity %d %%", this.serialNumber, this.batteryCapacity);
	}
}
