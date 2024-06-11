package com.chat.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class GroupMembers extends Audit {

	 @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private String id;

	 @ManyToOne
	 @JoinColumn(name = "group_id")
	 private ChatGroups groups;
	 
	 @ManyToOne
	 @JoinColumn(name ="user_id")
	 private User user;
	 
	 @Enumerated(EnumType.STRING)
	 private GroupRole userRole;
}
