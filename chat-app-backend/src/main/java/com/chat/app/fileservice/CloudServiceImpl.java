package com.chat.app.fileservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
@Service
public class CloudServiceImpl  implements CloudService{

	@Autowired
	public Cloudinary cloudinary;

	@Override
	public String uploadFileInFolder(MultipartFile myFile, String destinationPath) {

		String uuid = UUID.randomUUID().toString();
		System.err.println(myFile.getOriginalFilename());

		String randomName = uuid.concat(myFile.getOriginalFilename());
		String fileName = StringUtils.cleanPath(randomName);
		System.err.println(fileName);
		Map uploadResponse;
		try {
			uploadResponse = cloudinary.uploader().upload(myFile.getBytes(),
					  ObjectUtils.asMap("public_id", destinationPath +"/"+fileName)); 
			return (String)uploadResponse.get("secure_url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	 public String getFileExtension(MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        if (fileName != null && fileName.contains(".")) {
	            return fileName.substring(fileName.lastIndexOf(".") + 1);
	        }
	        return null; // or throw an exception if you want to handle this case differently
	    }
	@Override
	public String uploadFileInFolde(MultipartFile file, String destinationPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> uploadFileInFolder(List<MultipartFile> file, String destinationPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getImages(String fileName, String destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImageFromCloudServer(String imageUrl) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
