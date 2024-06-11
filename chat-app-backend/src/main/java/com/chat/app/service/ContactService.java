package com.chat.app.service;

import java.util.List;
import java.util.Map;

import com.chat.app.model.ContactStatus;
import com.chat.app.payload.ApiResponse;
import com.chat.app.payload.ContactResponse;

public interface ContactService {

	public ApiResponse updateContactStatus(ContactStatus status,String contactId);
	public  Map<String,List<ContactResponse>> getAllContact(String serachKey);
	public Boolean addContact(String receiverId,String sender);
	public ApiResponse deleteContact(String contactId);
}
