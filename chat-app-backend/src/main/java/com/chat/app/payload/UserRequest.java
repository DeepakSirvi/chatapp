package com.chat.app.payload;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	
	private String id;
	
	@NotBlank
	private String userName;
	private String userMobile;
	@NotBlank
	private String userPassword;
	@NotBlank
	private String userEmail;
	private String userProfile;
	
}
