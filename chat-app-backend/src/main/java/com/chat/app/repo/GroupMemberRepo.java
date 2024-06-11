package com.chat.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.app.model.GroupMembers;

public interface GroupMemberRepo extends JpaRepository<GroupMembers, String> {

}
