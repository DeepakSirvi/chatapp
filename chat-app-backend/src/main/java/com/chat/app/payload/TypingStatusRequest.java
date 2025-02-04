package com.chat.app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypingStatusRequest {

	private String userEmail;
	private Boolean status;
	private String conversionId;
}
