package com.chat.app.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.stereotype.Service;

@Service
public class NetworkServiceImpl {

    public String getLocalIpAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unable to determine IP address";
        }
    }
}