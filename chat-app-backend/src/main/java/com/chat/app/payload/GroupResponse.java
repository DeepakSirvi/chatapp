package com.chat.app.payload;


import java.util.List;

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
public class GroupResponse extends AuditResponse {

	 private String id;
	 private String groupName;
	 private String groupDescription;
	 private String groupProfile;
	 private List<GroupMemberResponse> groupMembers;
}
