package com.chat.app.service;

import java.util.List;

import com.chat.app.model.InvitationStatus;
import com.chat.app.payload.ApiResponse;
import com.chat.app.payload.InvitationRequest;
import com.chat.app.payload.InvitationResponse;

public interface InvitationService {

	public ApiResponse sendInvitation(InvitationRequest invitationRequest); 
	public List<InvitationResponse> getAllInvitation();
	public ApiResponse updateInvitationStatus(InvitationStatus status,String invitationId);
}
