package com.chat.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.model.InvitationStatus;
import com.chat.app.payload.InvitationRequest;
import com.chat.app.service.InvitationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chatapp/invitation")
@CrossOrigin
@RequiredArgsConstructor
public class InvitationController {

	private final InvitationService invitationService;
	@PostMapping
	public ResponseEntity<?> sendInvitation(@Valid @RequestBody InvitationRequest invitationRequest)
	{
		return new ResponseEntity<>(invitationService.sendInvitation(invitationRequest),HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<?> updateStatus(@RequestParam(value ="status") InvitationStatus status,@RequestParam(value="invitationId") String invitationId)
	{
		return new ResponseEntity<>(invitationService.updateInvitationStatus(status, invitationId),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllInvitation()
	{
		return new ResponseEntity<>(invitationService.getAllInvitation(),HttpStatus.OK);
	}
}
