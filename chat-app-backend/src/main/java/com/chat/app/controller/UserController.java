package com.chat.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chatapp/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	

	@GetMapping("/currentUser")
	public ResponseEntity<?> currentUser()
	{
//		this.socketModule.sendMessageToUser(, ,"userOnline");
		return new ResponseEntity<>(userService.currentUser(),HttpStatus.OK);
	}
	
	 @PostMapping("/upload")
	 public ResponseEntity<?> uploadProfile(@RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto){
	        return new ResponseEntity<>(userService.uploadImage(profilePhoto),HttpStatus.OK);
}
	 
}
