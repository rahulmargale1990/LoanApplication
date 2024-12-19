package com.bank.loan.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bank.loan.payload.UploadFileResponse;
import com.bank.loan.service.FileStorageService;

import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

@RestController
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
    @Autowired  
	private FileStorageService fileStorageService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
    	String fileName= fileStorageService.storeFile(file);
    	String fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile")
    			.path(fileName).toUriString();
    	
         return new UploadFileResponse(fileName,fileDownloadUri,file.getContentType(),file.getSize());
         
    	
    	
    }
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files){
    	
    	return Arrays.asList(files).stream().map(file->uploadFile(file)).collect(Collectors.toList());
    }
   
    @GetMapping("/downloadFile/{FileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,HttpServletRequest req){
    	
    	//load files as resource
    	
    	Resource resouce = fileStorageService.loadFileAsResource(fileName);
    	
    	// determine file content type
    	String contentType =null;
    	try {
    		contentType=req.getServletContext().getMimeType(resouce.getFile().getAbsolutePath());
    	}catch(IOException ex) {
    		logger. info("could not determine file type");
    	}
    	if(contentType ==null) {
    		contentType="application/octet-stream";
    	}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resouce.getFilename() + "\"").body(resouce);
    	
    }
    
    

}
