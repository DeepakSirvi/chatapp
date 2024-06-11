package com.chat.app.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	private Boolean success;
	private String message;
	private HttpStatus status;

	public ApiResponse(String message) {
		this.message = message;
		this.success = Boolean.TRUE;
		this.status = HttpStatus.OK;
	}
}
