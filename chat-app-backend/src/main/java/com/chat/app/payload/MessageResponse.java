package com.chat.app.payload;

import java.util.List;

import com.chat.app.model.Message;
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
public class MessageResponse extends AuditResponse {

	 	private String id;
		private UserResponse sender;
		private UserResponse receiver;
		private String message;
		private List<FileResponse> files;
		public MessageResponse messageToResponse(Message message) {
			this.setMessage(message.getMessage());
			this.setId(message.getId());
			this.setCreatedBy(message.getCreatedBy());
			this.setCreatedAt(message.getCreatedAt());
			this.setUpdatedBy(message.getUpdatedBy());
			this.setUpdatedAt(message.getUpdatedAt());
			return this;
		}

}
