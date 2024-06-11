package com.chat.app.payload;

import com.chat.app.model.File;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
	
	    private String id;
	    private String fileName;
	    private String fileType;
	    private String fileUrl;
	    private Long fileSize;
	    
	    public FileResponse fileToResponse(File file) {
	    	this.setId(file.getId());
	    	this.setFileName(file.getFileName());
	    	this.setFileSize(file.getFileSize());
	    	this.setFileType(file.getFileType());
	    	this.setFileUrl(file.getFileUrl());
	    	return this;
	    }

}
