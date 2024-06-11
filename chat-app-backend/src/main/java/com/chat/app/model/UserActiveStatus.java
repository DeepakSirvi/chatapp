package com.chat.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserActiveStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private LocalDateTime lastSeen;
	private Boolean isOnline;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
