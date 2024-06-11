package com.chat.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.exception.BadRequestException;
import com.chat.app.fileservice.CloudService;
import com.chat.app.model.User;
import com.chat.app.model.UserStatus;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.UserRepository;
import com.chat.app.service.UserService;
import com.chat.app.util.AppUtils;
import com.chat.app.util.Constants;
import com.chat.app.util.JwtUtils;

@Service

public class UserServiceImpl implements UserService {

	@Autowired
	private   UserRepository userRepo;
	@Autowired
	private   JwtUtils jwtUtils;
	@Autowired
	private   AppUtils appUtils;
	@Autowired
	private   CloudService cloudService;

	
	@Override
	public UserResponse currentUser() {
		String userName = jwtUtils.getUserName(jwtUtils.getToken());
		Optional<User> user1 = userRepo.findByUserEmail(userName);
		if (user1.isPresent()) {
			User user = user1.get();
			if (user.getUserStatus().equals(UserStatus.ACTIVE)) {
				UserResponse userResponse = new UserResponse();
				userResponse.userToResponse(user);
				userResponse.setUserProfile(user.getUserProfile());

				return userResponse;
			}
			throw new BadRequestException(Constants.DEACTIVE_USER);
		}
		throw new BadRequestException(Constants.PLEASE_LOGIN);
	}

	@Override
	public UserResponse uploadImage(MultipartFile profilePhoto) {
	User user = userRepo.findById(appUtils.getUserId()).get();
	if (profilePhoto != null) {
		String profileName = cloudService.uploadFileInFolder(profilePhoto,"chatapp");
		user.setUserProfile(profileName);
		user = userRepo.save(user);
	}
	UserResponse userResponse = new UserResponse();
	userResponse.userToResponse(user);
		return userResponse;
	}

	
}
