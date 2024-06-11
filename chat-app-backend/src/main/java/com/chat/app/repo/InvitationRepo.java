package com.chat.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.app.model.Invitation;
import com.chat.app.model.InvitationStatus;
import com.chat.app.model.User;

public interface InvitationRepo extends JpaRepository<Invitation,String> {

	List<Invitation> findByReceiver(User user);

	Optional<Invitation> findByReceiverAndSender(User user, User user2);

	Optional<Invitation> findByReceiverAndSenderAndStatus(User user, User user2, InvitationStatus status);

	List<Invitation> findByReceiver(User user, Sort sort);

}
