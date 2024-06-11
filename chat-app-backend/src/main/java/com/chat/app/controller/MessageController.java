package com.chat.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chatapp/message")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

	private final MessageService messageService;
	@PostMapping
	public ResponseEntity<?> createMessage(@RequestPart(value = "message",required = false) String message,
			@RequestPart(value = "conversionId") String conversionId,
			@RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles)
	{
		return new ResponseEntity<>(messageService.createMessage(message,conversionId,multipartFiles),HttpStatus.OK);
	}

   

}
