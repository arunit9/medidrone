package com.app.medidrone.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class LoginRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
