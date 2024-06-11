package com.chat.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chat.app.exception.BadRequestException;
import com.chat.app.exception.ResourceNotFoundException;
import com.chat.app.model.User;
import com.chat.app.model.UserActiveStatus;
import com.chat.app.model.UserStatus;
import com.chat.app.payload.ApiResponse;
import com.chat.app.payload.LoginRequest;
import com.chat.app.payload.UserRequest;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.UserRepository;
import com.chat.app.service.AuthService;
import com.chat.app.util.Constants;
import com.chat.app.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

	private final UserRepository userRepo;
	private final ModelMapper mapper;
	private final JwtUtils jwtUtils;

	@Override
	public ApiResponse saveUser(UserRequest userRequest) {
		if (userRepo.existsByUserMobile(userRequest.getUserMobile())) {
			throw new BadRequestException(Constants.NUMBER_ALREADY_TAKEN);
		}

		User user = mapper.map(userRequest, User.class);
		user.setUserStatus(UserStatus.ACTIVE);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setUserProfile("https://res.cloudinary.com/dhgy9as8a/image/upload/v1714725842/image_yqh9e1.png");	
			ApiResponse apiResponse = new ApiResponse();
			UserActiveStatus  activeStatus = new UserActiveStatus();
			activeStatus.setIsOnline(false);
			activeStatus.setLastSeen(null);
			activeStatus.setUser(user);
			user.setActiveStatus(activeStatus);
		user = userRepo.save(user);
		if (user.getId() != null) {
			apiResponse = new ApiResponse(Boolean.TRUE, Constants.RESGISTRATION_SUCCESSFULLY, HttpStatus.CREATED);
		}
		return apiResponse;
	}

	@Override
	public UserResponse loginUser(LoginRequest loginRequest) {
		User user = userRepo.findByUserEmailAndPassword(loginRequest.getUserEmail(), loginRequest.getUserPassword())
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", loginRequest.getUserEmail()));
		if (user.getUserStatus().equals(UserStatus.ACTIVE)) {
			UserResponse userResponse = UserResponse.builder().id(user.getId()).build();
			userResponse.setToken(jwtUtils.generateToken(user.getUserEmail(), user.getId()));

			return userResponse;
		}

		throw new BadRequestException(Constants.DEACTIVE_USER);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> findByUserName = userRepo.findByUserEmail(username);
		if (findByUserName.isPresent()) {
			List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
			return new org.springframework.security.core.userdetails.User(username, username, authority);
		}
		throw new BadRequestException(Constants.PLEASE_LOGIN);
	}

	

}
