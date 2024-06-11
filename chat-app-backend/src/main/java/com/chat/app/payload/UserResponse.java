package com.chat.app.payload;


import java.time.LocalDateTime;

import com.chat.app.model.User;
import com.chat.app.model.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserResponse  {

	private String id;
	private String userName;
	private String userMobile;
	private String userEmail;
	private UserStatus userStatus;
	private String userProfile;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime createdAt;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime updatedAt;
	private String token;
	private UserActiveResponse  activeResponse=new UserActiveResponse();

	
	public UserResponse userToResponse(User user) {
		this.setId(user.getId());
		this.setUserName(user.getUserName());
		this.setUserEmail(user.getUserEmail());
		this.setUserMobile(user.getUserEmail());
		this.setCreatedAt(user.getCreatedAt());
		this.setUpdatedAt(user.getUpdatedAt());
		this.setUserStatus(user.getUserStatus());
		this.setUserProfile(user.getUserProfile());
		this.getActiveResponse().setId(user.getActiveStatus().getId());
		this.getActiveResponse().setIsOnline(user.getActiveStatus().getIsOnline());
		this.getActiveResponse().setLastSeen(user.getActiveStatus().getLastSeen());
		return this;
	}

	public UserResponse(String id2) {
		id=id2;
	}
}
