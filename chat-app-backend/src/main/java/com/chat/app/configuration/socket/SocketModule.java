package com.chat.app.configuration.socket;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.app.exception.ResourceNotFoundException;
import com.chat.app.model.Conversion;
import com.chat.app.model.Message;
import com.chat.app.model.User;
import com.chat.app.payload.TypingStatusRequest;
import com.chat.app.payload.UserResponse;
import com.chat.app.repo.ConversionRepo;
import com.chat.app.repo.UserRepository;
import com.chat.app.service.SocketService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

@Component
public class SocketModule {
	
	Logger log = LoggerFactory.getLogger(SocketModule.class);

	public Map<String, SocketIOClient> allClientsData = new HashMap<>();
	@Autowired
	private   ConversionRepo conversionRepo;
	@Autowired
	private SocketService socketService;
	@Autowired
	private UserRepository userRepo;
	
	public SocketModule(SocketIOServer server,SocketService socketService) {
		this.socketService=socketService;
		server.addConnectListener(this.onConnected());
		server.addDisconnectListener(this.onDisconnected());
		server.addEventListener("sendMessage", Message.class, this.onChatReceived());
		server.addEventListener("user_typing",TypingStatusRequest.class,this.typing());
	}
	
	

	private DataListener<TypingStatusRequest>  typing() {
	    return (client, data,ack) -> {
	    	sendMessageToUser(data.getUserEmail(), data, "sender_typing");
	    };
		
	}



	private DataListener<Message> onChatReceived() {
		return (senderClient, data, ackSender) -> {
			log.info(data.toString());
			socketService.saveMessage(senderClient, data);
		};
	}

	private ConnectListener onConnected() {
		return (client) -> {
			var email = client.getHandshakeData().getSingleUrlParam("email");
			this.setMapData(email, client);
			updateUserStatus(email, true, null);
			log.info("Socket ID[{}] - room[{}]   Connected to chat module through", client, email);
		};

	}

	private void setMapData(String email, SocketIOClient client) {
		this.allClientsData.put(email, client);
	}

	private SocketIOClient getValueFromMap(String email) {
		SocketIOClient socketIOClient = this.allClientsData.get(email);
		return Objects.nonNull(socketIOClient) ? socketIOClient : null;
	}

	private DisconnectListener onDisconnected() {
		return client -> {
			var email = client.getHandshakeData().getSingleUrlParam("email");
			updateUserStatus(email, false,LocalDateTime.now());
			deleteMapData(email);
			log.info("Socket ID[{}] - room[{}] - username [{}]  discnnected to chat module through", client, email);
		};
	}

	private void deleteMapData(String email) {
		this.allClientsData.remove(email);
	}
	
	private void updateUserStatus(String email,Boolean status,LocalDateTime dateTime) {
		User user = userRepo.findByUserEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
		user.getActiveStatus().setIsOnline(status);
		if(Objects.nonNull(dateTime))
		user.getActiveStatus().setLastSeen(dateTime);
		else
		user.getActiveStatus().setLastSeen(null);
		userRepo.save(user);
		UserResponse userResponse = new UserResponse();
	    userResponse.userToResponse(user);
	    List<Conversion> converstionList = conversionRepo.findByUserId(user.getId());
	    converstionList.stream().forEach(conversion -> {
	    	if (conversion.getSender().getId().equals(user.getId())) {
	    		sendMessageToUser(conversion.getReceiver().getUserEmail(), userResponse, "userOnline");
			} else {
				sendMessageToUser(conversion.getSender().getUserEmail(), userResponse, "userOnline");
			}
	    });
	}
	
	
	public void sendMessageToUser(String email,Object message,String room){
		this.socketService.sendSocketmessage(this.getValueFromMap(email), message, room);
	}
}
