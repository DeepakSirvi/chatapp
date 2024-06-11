package com.chat.app.service.impl;

import org.springframework.stereotype.Service;

import com.chat.app.model.Message;
import com.chat.app.service.SocketService;
import com.corundumstudio.socketio.SocketIOClient;

@Service
public class SocketServiceImpl implements SocketService {
	
    public void sendSocketmessage(SocketIOClient senderClient, Object message, String room) {
    	if (senderClient!=null&&senderClient.getSessionId()!=null) {
    		senderClient.sendEvent(room, message);
    	}	
    }

    public void saveMessage(SocketIOClient senderClient, Message message) {

////        Message storedMessage = messageService.saveMessage(
////            Message.builder()
////                .messageType(MessageType.CLIENT)
////                .message(message.getMessage())
////                .room(message.getRoom())
////                .username(message.getUsername())
////                .build()
////        );
//
////        sendSocketmessage(senderClient, storedMessage, message.getRoom());

    }

    public void saveInfoMessage(SocketIOClient senderClient, String message, String room) {
//        Message storedMessage = messageService.saveMessage(
//            Message.builder()
//                .messageType(MessageType.SERVER)
//                .message(message)
//                .room(room)
//                .build()
//        );

//        sendSocketmessage(senderClient, storedMessage, room);
    }
}