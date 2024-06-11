package com.chat.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.payload.LoginRequest;
import com.chat.app.payload.UserRequest;
import com.chat.app.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chatapp/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@Valid @RequestBody
			UserRequest userRequest)
	{
		return new ResponseEntity<>(authService.saveUser(userRequest),HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest)
	{
		return new ResponseEntity<>(authService.loginUser(loginRequest),HttpStatus.OK);
	}


}
