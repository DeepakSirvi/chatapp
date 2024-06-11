package com.chat.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chat.app.exception.ResourceNotFoundException;
import com.chat.app.model.Contact;
import com.chat.app.model.ContactStatus;
import com.chat.app.model.User;
import com.chat.app.payload.ApiResponse;
import com.chat.app.payload.ContactResponse;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.ContactRepo;
import com.chat.app.service.ContactService;
import com.chat.app.util.AppUtils;
import com.chat.app.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

	private final ContactRepo contactRepo;
	private final AppUtils appUtils;

	@Override
	public ApiResponse updateContactStatus(ContactStatus status, String contactId) {
		Contact contact = contactRepo.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact", "id", contactId));
		AppUtils.genericStatusValidation(status, contact.getStatus());
		contact.setStatus(status);
		contactRepo.save(contact);
		return new ApiResponse(Constants.STATUS_UPDATE);
	}

	@Override
	public Map<String,List<ContactResponse>> getAllContact(String serachKey) {
		List<Contact> contacts = new ArrayList<>();
		if (!serachKey.equals("")) {
			contacts = contactRepo.findByOwnerIdOrReceiverIdAndSearchTermOrderByUsername(appUtils.getUserId(), serachKey);
		} else {
			contacts = contactRepo.findByOwnerIdOrReceiverIdOrderByUsername(appUtils.getUserId());
		}
		List<ContactResponse> contactListToResponseList = contactListToResponseList(contacts);
		Map<String,List<ContactResponse>> responseMap = new HashMap<>();
		
		 for (ContactResponse contact : contactListToResponseList) {
		        String firstLetter = contact.getUser().getUserName().substring(0, 1);
		        responseMap.computeIfAbsent(firstLetter, k -> new ArrayList<>()).add(contact);
		    }
	    return responseMap;
	}

	@Override
	public Boolean addContact(String receiverId, String sender) {
		Optional<Contact> oldContact = contactRepo.findByOwnerIdAndReceiverId(receiverId, sender);
		if (oldContact.isPresent()) {
			System.err.println("Deepak");
			return false;
		} else {
			Contact contact = new Contact();
			contact.setOwner(new User(sender));
			contact.setReceiver(new User(receiverId));
			contact.setStatus(ContactStatus.UNBLOCK);
			contactRepo.save(contact);
			return true;
		}
	}

	@Override
	public ApiResponse deleteContact(String contactId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private List<ContactResponse> contactListToResponseList(List<Contact> contactList){
	return contactList.stream().map(contact -> {
        ContactResponse response = new ContactResponse();
        response.contactToResposne(contact);
        if (!contact.getOwner().getId().equals(appUtils.getUserId())) {
            response.setUser(new UserResponse().userToResponse(contact.getOwner()));
        } else {
            response.setUser(new UserResponse().userToResponse(contact.getReceiver()));
        }
        return response;
    }).collect(Collectors.toList());
	}

}
