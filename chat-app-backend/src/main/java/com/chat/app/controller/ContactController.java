package com.chat.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.service.ContactService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chatapp/contact")
@CrossOrigin
@RequiredArgsConstructor
public class ContactController {
	
	private final ContactService contactService;

	@GetMapping
	public ResponseEntity<?> getAllContact(@RequestParam(name = "search")  String searchkey)
	{
		return new ResponseEntity<>(contactService.getAllContact(searchkey),HttpStatus.OK);
	}
}
