package com.chat.app.service;

import com.chat.app.payload.ApiResponse;
import com.chat.app.payload.LoginRequest;
import com.chat.app.payload.UserRequest;
import com.chat.app.payload.UserResponse;

public interface AuthService {
	
	public ApiResponse saveUser(UserRequest  userRequest);
	public UserResponse loginUser(LoginRequest loginRequest);
	

}
