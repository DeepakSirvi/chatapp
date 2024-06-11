package com.chat.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chat.app.configuration.socket.SocketModule;
import com.chat.app.exception.BadRequestException;
import com.chat.app.exception.ResourceNotFoundException;
import com.chat.app.model.Invitation;
import com.chat.app.model.InvitationStatus;
import com.chat.app.model.User;
import com.chat.app.payload.ApiResponse;
import com.chat.app.payload.InvitationRequest;
import com.chat.app.payload.InvitationResponse;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.InvitationRepo;
import com.chat.app.repo.UserRepository;
import com.chat.app.service.ContactService;
import com.chat.app.service.InvitationService;
import com.chat.app.util.AppUtils;
import com.chat.app.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

	private final AppUtils appUtils;
	private final UserRepository userRepository;
	private final InvitationRepo invitationRepo;
	private final SocketModule socketModule;
	private final ContactService contactService;
	

	@Override
	public ApiResponse sendInvitation(InvitationRequest invitationRequest) {
		User recevier = userRepository.findByUserEmail(invitationRequest.getUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("USER", "Email", invitationRequest.getUserEmail()));
		Optional<Invitation> oldInvitationPending = invitationRepo.findByReceiverAndSenderAndStatus(
				new User(recevier.getId()), new User(appUtils.getUserId()), InvitationStatus.PENDING);
		if (oldInvitationPending.isPresent()) {
			throw new BadRequestException(Constants.INVITATION_ALREADY_SENDED + " : " + invitationRequest.getUserEmail()
					+ " and status is :" + oldInvitationPending.get().getStatus());
		}
		Optional<Invitation> oldInvitationAccepted = invitationRepo.findByReceiverAndSenderAndStatus(
				new User(recevier.getId()), new User(appUtils.getUserId()), InvitationStatus.ACCEPTED);
		if (oldInvitationAccepted.isPresent()) {
			throw new BadRequestException(Constants.INVITATION_ALREADY_SENDED + " : " + invitationRequest.getUserEmail()
					+ " and status is :" + oldInvitationAccepted.get().getStatus());
		}
		Invitation invitation = new Invitation();
		if (invitationRequest.getDescription() != "") {
			invitation.setDescription(invitationRequest.getDescription());
		} else {
			invitation.setDescription(Constants.INVITATION_MESSAGE);
		}
		invitation.setSender(new User(appUtils.getUserId()));
		invitation.setReceiver(new User(recevier.getId()));
		invitation.setStatus(InvitationStatus.PENDING);
		Invitation save = invitationRepo.save(invitation);
		this.socketModule.sendMessageToUser(invitationRequest.getUserEmail(), save,"sendinvitation");
		return new ApiResponse(Constants.INVITATION_SENDED + " : " + invitationRequest.getUserEmail());
	}

	@Override
	public List<InvitationResponse> getAllInvitation() {
		Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
		List<Invitation> listInvitation = invitationRepo.findByReceiver(new User(appUtils.getUserId()),sort);
		List<InvitationResponse> listResponse = listInvitation.stream().map(invitation -> {
			InvitationResponse invitationResponse = new InvitationResponse();
			invitationResponse.invitationToResponse(invitation);
			invitationResponse.setSender(new UserResponse().userToResponse(invitation.getSender()));
			invitationResponse.setReceiver(new UserResponse().userToResponse(invitation.getReceiver()));
			return invitationResponse;
		}).collect(Collectors.toList());
		return listResponse;
	}

	@Override
	public ApiResponse updateInvitationStatus(InvitationStatus status, String invitationId) {
		Invitation invitation = invitationRepo.findById(invitationId)
				.orElseThrow(() -> new ResourceNotFoundException("Invitation", "id", invitationId));
		if (invitation.getStatus().equals(InvitationStatus.PENDING)) {
			AppUtils.genericStatusValidation(status, invitation.getStatus());
			invitation.setStatus(status);
			invitationRepo.save(invitation);
			contactService.addContact(invitation.getReceiver().getId(),invitation.getSender().getId());
			
			
			Optional<Invitation> oldInvitationPending = invitationRepo.findByReceiverAndSenderAndStatus(
					invitation.getSender(),invitation.getReceiver(),InvitationStatus.PENDING);
			if(oldInvitationPending.isPresent()) {
				System.err.println("Yes");
				invitationRepo.deleteById(oldInvitationPending.get().getId());
			}
			return new ApiResponse(Constants.STATUS_UPDATE);
		}
		throw new BadRequestException(Constants.INVALID_TRANSITION);
	}

}
