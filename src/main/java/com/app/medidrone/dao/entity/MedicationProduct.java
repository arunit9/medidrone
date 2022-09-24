package com.app.medidrone.dao.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
public class MedicationProduct implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable=false)
	private String code;

    @Column(nullable=false)
	private String name;

    @Column(nullable=false)
	private String image;

	public MedicationProduct(String code, String name, String image) {
		super();
		this.code = code;
		this.name = name;
		this.image = image;
	}
}
