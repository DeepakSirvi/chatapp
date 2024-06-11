package com.chat.app.service;

import java.util.List;

import com.chat.app.payload.ConversionResponse;

public interface ConversionService {

	ConversionResponse createConversion(String contactId);

	List<ConversionResponse> getUserChats();

	ConversionResponse getMessage(String converstionId);

}
