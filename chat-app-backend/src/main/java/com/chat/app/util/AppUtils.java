package com.chat.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.exception.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AppUtils {

	@Autowired
	private JwtUtils jwtUtils;

	public String getUserId() {
		HttpServletRequest httpRequest = RequestContextHolder.getRequest();
		if (httpRequest != null) {
			String token = httpRequest.getParameter("Authorization");
			return jwtUtils.getUserIdFromToken(token);
		} else {
			throw new BadRequestException(Constants.INVALID_REQUEST);
		}
	}

	public InputStream getImages(String fileName) {
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <U> void genericStatusValidation(U currentStatus,U newStatus) {
		if(currentStatus.equals(newStatus)) {
			throw new BadRequestException(Constants.INVALID_TRANSITION);
		}
		
	}



}
