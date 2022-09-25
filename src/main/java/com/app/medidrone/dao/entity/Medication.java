package com.app.medidrone.dao.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medication")
@Getter @Setter @NoArgsConstructor
@ToString
public class Medication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
   	private Integer weight;

//    @Column(nullable = false)
//	private String code;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(	name = "med_product", 
				joinColumns = @JoinColumn(name = "id"), 
				inverseJoinColumns = @JoinColumn(name = "code"))
    private MedicationProduct medicationProduct;

	public Medication(Integer weight, MedicationProduct medicationProduct) {//, String code) {
		super();
		this.weight = weight;
		this.medicationProduct = medicationProduct;
	}

}
