package com.severine.api.danse.web.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.severine.api.danse.service.PictureService;
import com.severine.api.danse.shared.S3Constants;
import com.severine.api.danse.shared.Utils;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class PictureController {

	@Autowired
	private Utils utils;
	
	@Autowired
	private PictureService application;
	
	@RequestMapping(value = "/pictures/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deletePicture(@PathVariable Long id) {
		try {
			application.deletePicture(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@CrossOrigin
	@RequestMapping(value = "/pictures", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Reponse uploadImage(@RequestParam("image") MultipartFile[] submissions) {
		try {
			saveUploadedFiles(submissions);
		} catch (IOException e) {
			System.err.println(e);
			return new Reponse(1, "Une erreur est survenue lors du téléchargement de l'image");
		}
		return new Reponse(1, "post");
	}

	private void saveUploadedFiles(MultipartFile[] files) throws IOException {
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue; // next pls
			}
	        utils.savePicture(S3Constants.BUCKET_TMP, S3Constants.BUCKET_DOMAIN, file.getOriginalFilename(), convert(file));
		}

	}
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
}
