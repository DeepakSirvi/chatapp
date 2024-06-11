package com.chat.app.payload;

import com.chat.app.model.GroupRole;
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
public class GroupMemberResponse {
	
    private String id;

	 private GroupResponse groups;

	 private UserResponse user;
	 
	
	 private GroupRole userRole;

}