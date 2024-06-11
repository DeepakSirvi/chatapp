package com.chat.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.app.model.Conversion;
import com.chat.app.model.User;

public interface ConversionRepo  extends JpaRepository<Conversion, String> {

	Optional<Conversion> findBySenderAndReceiverOrReceiverAndSender(User owner, User receiver, User owner2,
			User receiver2);


	 @Query("SELECT c FROM Conversion c WHERE c.sender.id = :userId OR c.receiver.id = :userId " +
	            "ORDER BY (SELECT MAX(m.createdAt) FROM Message m WHERE m.conversion = c) DESC")
	    List<Conversion> findAllConversionsWithLatestMessageSortedByDate(
	            @Param("userId") String userId
	    );

		
		@Query("SELECT c FROM Conversion c left JOIN FETCH c.message m WHERE c.id = :conversionId ORDER BY m.createdAt ASC")
	    Optional<Conversion> findByIdWithMessagesOrderByCreatedAtDesc(@Param("conversionId") String conversionId);

		 @Query("SELECT c FROM Conversion c WHERE c.sender.id = :userId OR c.receiver.id = :userId ")
		List<Conversion> findByUserId(@Param("userId") String userId);

}
