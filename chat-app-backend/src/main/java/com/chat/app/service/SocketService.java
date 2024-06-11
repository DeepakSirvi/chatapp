package com.chat.app.service;

import com.chat.app.model.Message;
import com.corundumstudio.socketio.SocketIOClient;

public interface SocketService {

    
    public void sendSocketmessage(SocketIOClient senderClient, Object message, String room) ;

    public void saveMessage(SocketIOClient senderClient, Message message);

    public void saveInfoMessage(SocketIOClient senderClient, String message, String room);
}