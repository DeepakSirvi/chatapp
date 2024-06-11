package com.chat.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.configuration.socket.SocketModule;
import com.chat.app.exception.ResourceNotFoundException;
import com.chat.app.fileservice.CloudService;
import com.chat.app.model.Conversion;
import com.chat.app.model.File;
import com.chat.app.model.Message;
import com.chat.app.model.User;
import com.chat.app.payload.FileResponse;
import com.chat.app.payload.MessageResponse;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.ConversionRepo;
import com.chat.app.repo.MessageRepository;
import com.chat.app.repo.UserRepository;
import com.chat.app.service.MessageService;
import com.chat.app.util.AppUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;
	private final ConversionRepo conversionRepo;
	private final AppUtils appUtils;
	private final SocketModule socketModule;
	private final UserRepository userRepository;
	private final CloudService cloudService;

	@Override
	public Object createMessage(String message, String conversionId, List<MultipartFile> multipartFiles) {
		Conversion conversion = conversionRepo.findById(conversionId)
				.orElseThrow(() -> new ResourceNotFoundException("Conversion", "id", conversionId));
		Message message2 = new Message();
		message2.setMessage(message);
		message2.setSender(new User(appUtils.getUserId()));
		String receiverId = null;
		if (conversion.getSender().getId().equals(appUtils.getUserId())) {
			receiverId = conversion.getReceiver().getId();
		} else {
			receiverId = conversion.getSender().getId();
		}
		message2.setReceiver(new User(receiverId));
		message2.setConversion(new Conversion(conversionId));
		List<File> files = new ArrayList<>();
		if (Objects.nonNull(multipartFiles)) {
			multipartFiles.stream().forEach(file -> {
				if (Objects.nonNull(file)) {
					String name = cloudService.uploadFileInFolder(file, "chatapp");
					File newFile = new File();
					newFile.setFileName(file.getOriginalFilename());
					newFile.setFileSize(file.getSize());
					newFile.setFileType(cloudService.getFileExtension(file));
					newFile.setFileUrl(name);
					newFile.setMessage(message2);
					files.add(newFile);
				}
			});
		}
		message2.setFiles(files);
		Message save = messageRepository.save(message2);
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.messageToResponse(message2);
		messageResponse.setReceiver(new UserResponse(save.getReceiver().getId()));
		messageResponse.setFiles(save.getFiles().stream().map(f ->{
			FileResponse fileResponse = new FileResponse();
			return fileResponse.fileToResponse(f);
		}).collect(Collectors.toList()));
		this.socketModule.sendMessageToUser(userRepository.findById(save.getReceiver().getId()).get().getUserEmail(),
				messageResponse, "sendMessage");
		return messageResponse;
	}

}
