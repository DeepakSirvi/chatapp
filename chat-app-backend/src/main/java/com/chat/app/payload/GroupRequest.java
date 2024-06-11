package com.chat.app.payload;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
	
	 private String id;
	 @NotBlank
	 private String groupName;
	 @NotBlank
	 private String groupDescription;
	 private String groupProfile;
	 private List<GroupMemberRequest> groupMembers;

}
