package com.chat.app.configuration.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import lombok.RequiredArgsConstructor;

	@Component
	@RequiredArgsConstructor
	public class ServerCommandLineRunner  implements CommandLineRunner {
	
	    private final SocketIOServer server;
	    
	    private Logger logger = LoggerFactory.getLogger(SocketIoConfiguration.class);
	
	    @Override
	    public void run(String... args) throws Exception {
	        server.start();
	        logger.info("Server started");
	    }
	}