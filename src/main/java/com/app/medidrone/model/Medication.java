package com.app.medidrone.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class Medication {

	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9-_]+$")
	private String name;

	@NotNull
	@Min(value = 1, message 
		= "weight must be greater than or equal to 1mgr")
   	private Integer weight;

	@NotBlank
	@Size(min = 5, max = 20, message 
		      = "Code must be between 5 and 20 characters")
	@Pattern(regexp = "^[A-Z0-9_]+$")
	private String code;

	private String image;

	public Medication(String name, Integer weight, String code, String image) {
		super();
		this.name = name;
		this.weight = weight;
		this.code = code;
		this.image = image;
	}

}
