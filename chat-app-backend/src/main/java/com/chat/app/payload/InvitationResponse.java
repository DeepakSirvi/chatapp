package com.chat.app.payload;

import com.chat.app.model.Invitation;
import com.chat.app.model.InvitationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class InvitationResponse extends AuditResponse {

	private String id;
	private String description;
	private UserResponse sender;
	private UserResponse receiver;
	private InvitationStatus status;
	
	public InvitationResponse invitationToResponse(Invitation invitation ) {
		this.setId(invitation.getId());
		this.setDescription(invitation.getDescription());
		this.setCreatedAt(invitation.getCreatedAt());
		this.setUpdatedAt(invitation.getUpdatedAt());
		this.setCreatedBy(invitation.getCreatedBy());
		this.setUpdatedBy(invitation.getUpdatedBy());
		this.setStatus(invitation.getStatus());
		return this;
	}
}
