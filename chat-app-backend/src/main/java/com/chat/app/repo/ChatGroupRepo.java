package com.chat.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.app.model.ChatGroups;

public interface ChatGroupRepo extends JpaRepository<ChatGroups, String>  {
	

}
