package com.chat.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.app.model.User;

public interface UserRepository extends JpaRepository<User, String>  {

	boolean existsByUserMobile(String userMobile);

	Optional<User> findByUserEmail(String username);

	Optional<User> findByUserEmailAndPassword(String userEmails, String userPassword);

}
