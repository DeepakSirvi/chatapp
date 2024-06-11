package com.chat.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.app.model.Contact;

public interface ContactRepo extends JpaRepository<Contact, String> {

	Optional<Contact> findByOwnerIdAndReceiverId(String receiverId, String sender);

	List<Contact> findAllByOwnerIdOrReceiverId(String userId, String userId1);

	@Query("SELECT c FROM Contact c WHERE (c.owner.id = :userId OR c.receiver.id = :userId) AND (CASE WHEN c.receiver.id = :userId THEN c.owner.userName ELSE c.receiver.userName END) LIKE %:searchTerm% ORDER BY CASE WHEN c.receiver.id = :userId THEN c.owner.userName ELSE c.receiver.userName END")
	List<Contact> findByOwnerIdOrReceiverIdAndSearchTermOrderByUsername(@Param("userId") String userId,
	        @Param("searchTerm") String searchTerm);


	@Query("SELECT c FROM Contact c WHERE (c.owner.id = :userId OR c.receiver.id = :userId)  ORDER BY CASE WHEN c.receiver.id = :userId THEN c.owner.userName ELSE c.receiver.userName END")
	List<Contact> findByOwnerIdOrReceiverIdOrderByUsername(@Param("userId") String userId

	);

}
