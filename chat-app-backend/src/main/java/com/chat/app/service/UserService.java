package com.chat.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.chat.app.payload.UserResponse;

public interface UserService {
	public UserResponse currentUser();

	public UserResponse uploadImage(MultipartFile profilePhoto);

}
