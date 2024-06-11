package com.chat.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface MessageService {


	Object createMessage(String message, String conversionId, List<MultipartFile> multipartFiles);

}