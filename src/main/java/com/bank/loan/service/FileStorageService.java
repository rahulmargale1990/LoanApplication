package com.bank.loan.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bank.loan.exception.FileStorageException;
import com.bank.loan.property.FileStorageProperties;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.core.io.Resource;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageProperties fsp) {
		
		this.fileStorageLocation=Paths.get(fsp.getUploadDir()).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
			
		}catch(Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored ",ex);
		}
	}
	
	public String storeFile(MultipartFile file) {
		
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
		try {
			//check file name contains valid characters
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry ! file name contains invalid path "+fileName);
				
			}
			 // Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException ex) {
			throw new FileStorageException("could not store file "+fileName +" please try again!",ex);
		}
		return fileName;
	}

	public Resource loadFileAsResource(String fileName) {
		// TODO Auto-generated method stub
		Resource resource =null;
		try {
			Path filePath=fileStorageLocation.resolve(fileName).normalize();
			  resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}
			
		}catch(MalformedURLException  ex) {
			
		}
	   return resource;
	}

}
