package com.chat.app.configuration.socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.chat.app.service.impl.NetworkServiceImpl;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;


@Component
public class SocketIoConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(SocketIoConfiguration.class);

	 @Value("${socket-server.host}")
	    private String host;

	    @Value("${socket-server.port}")
	    private Integer port;

	    @Autowired
	    private NetworkServiceImpl networkServiceImpl;
	    @Bean
	    public SocketIOServer socketIOServer() {

//	    	host = networkServiceImpl.getLocalIpAddress();
	    	System.out.println(host);
	       Configuration config = new Configuration();
	        config.setHostname(host);
	        config.setPort(port);
	        logger.info("Socket successfully configure");
	        return new SocketIOServer(config);
	    }

}
