package com.chat.app.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.service.ConversionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chatapp/conversion")
@CrossOrigin
@RequiredArgsConstructor
public class ConverstionController {
	
	private final ConversionService  conversionService;

	@PostMapping("/{contactId}")
	public ResponseEntity<?> createConversion(@PathVariable(name = "contactId")  String contactId)
	{
		return new ResponseEntity<>(conversionService.createConversion(contactId),HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> getChats()
	{
		return new ResponseEntity<>(conversionService.getUserChats(),HttpStatus.OK);
	}
	
	@GetMapping("messages/{converstionId}")
	public ResponseEntity<?> getAllMessage(@PathVariable(name = "converstionId")  String converstionId)
	{
		return new ResponseEntity<>(conversionService.getMessage(converstionId),HttpStatus.OK);
	}
	
	
}
