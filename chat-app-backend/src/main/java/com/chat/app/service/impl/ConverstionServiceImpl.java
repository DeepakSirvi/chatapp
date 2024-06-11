package com.chat.app.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chat.app.exception.ResourceNotFoundException;
import com.chat.app.model.Contact;
import com.chat.app.model.Conversion;
import com.chat.app.model.Message;
import com.chat.app.payload.ConversionResponse;
import com.chat.app.payload.FileResponse;
import com.chat.app.payload.MessageResponse;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.ContactRepo;
import com.chat.app.repo.ConversionRepo;
import com.chat.app.service.ConversionService;
import com.chat.app.util.AppUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConverstionServiceImpl implements ConversionService {

	private final ConversionRepo conversionRepo;
	private final ContactRepo contactRepo;
	private final AppUtils appUtils;

	@Override
	public ConversionResponse createConversion(String contactId) {
		Contact contact = contactRepo.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact", "id", contactId));
		Optional<Conversion> conversion = conversionRepo.findBySenderAndReceiverOrReceiverAndSender(contact.getOwner(),
				contact.getReceiver(), contact.getOwner(), contact.getReceiver());
		if (conversion.isPresent()) {
			return conversionToResponse(conversion.get());
		} else {
			Conversion newConversion = new Conversion();
			newConversion.setReceiver(contact.getReceiver());
			newConversion.setSender(contact.getOwner());
			Conversion save = conversionRepo.save(newConversion);
			return getMessage(save.getId());
		}

	}

	@Override
	public List<ConversionResponse> getUserChats() {
		List<Conversion> collect = conversionRepo.findAllConversionsWithLatestMessageSortedByDate(appUtils.getUserId());	
		List<ConversionResponse> conversionList = collect.stream().map(conversion -> {
			
			if(conversion.getMessage().size()!=0)
			{
				Optional<Message> latestMessage = conversion.getMessage().stream()
				        .max(Comparator.comparing(Message::getCreatedAt));
				if(latestMessage.isPresent()) {
				Message message=latestMessage.get();
				conversion.setMessage(null);
				conversion.setMessage(new ArrayList<>(Collections.singletonList(message)));
				}
			}
			return conversionToResponse(conversion);
		}).collect(Collectors.toList());
		return conversionList;
	}

	@Override
	public ConversionResponse getMessage(String converstionId) {
		return conversionToResponse(conversionRepo.findByIdWithMessagesOrderByCreatedAtDesc(converstionId)
				.orElseThrow(() -> new ResourceNotFoundException("Conversion", "id", converstionId)));
	}

	private ConversionResponse conversionToResponse(Conversion conversion) {
		ConversionResponse response = new ConversionResponse();
		response.setId(conversion.getId());
		response.setCreatedBy(conversion.getCreatedBy());
		response.setCreatedAt(conversion.getCreatedAt());
		response.setUpdatedBy(conversion.getUpdatedBy());
		response.setUpdatedAt(conversion.getUpdatedAt());
		if (!conversion.getSender().getId().equals(appUtils.getUserId())) {
			response.setUser(new UserResponse().userToResponse(conversion.getSender()));
		} else {
			response.setUser(new UserResponse().userToResponse(conversion.getReceiver()));
		}
		response.setMessage(conversion.getMessage().stream().map(message -> {
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setFiles(message.getFiles().stream().map(f ->{
				FileResponse fileResponse = new FileResponse();
				return fileResponse.fileToResponse(f);
			}).collect(Collectors.toList()));
			
			return messageResponse.messageToResponse(message);

		}).collect(Collectors.toList()));
		return response;
	}

}
