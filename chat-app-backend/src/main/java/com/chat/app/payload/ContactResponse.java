package com.chat.app.payload;

import com.chat.app.model.Contact;
import com.chat.app.model.ContactStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ContactResponse extends AuditResponse {
	private String id;
	
	private UserResponse user;
	
	private ContactStatus status;

	public ContactResponse contactToResposne(Contact con) {
		this.setId(con.getId());
		this.setStatus(con.getStatus());
		return this;
	}


}
