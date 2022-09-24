package com.app.medidrone.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
		super();
		this.message = message;
	}

}
