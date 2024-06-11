package com.chat.app.payload;

import com.chat.app.model.GroupRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberRequest {
	
     private String id;
	 private GroupRequest groups; 
	 private UserRequest user;
	 private GroupRole userRole;

}
