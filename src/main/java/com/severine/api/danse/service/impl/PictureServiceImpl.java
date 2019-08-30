package com.severine.api.danse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.severine.api.danse.entities.Picture;
import com.severine.api.danse.repositories.PictureRepository;
import com.severine.api.danse.service.PictureService;

@Service("picture")
public class PictureServiceImpl implements PictureService{
	
	@Autowired
	private PictureRepository pictureRepository;
	
	@Override
	public void deletePicture(Long id) {
		Optional<Picture> picture = pictureRepository.findById(id);
		if(picture.isPresent()) {
			Picture p = picture.get();
			p.setIdProduct(null);
			pictureRepository.save(p);
			pictureRepository.delete(p);			
		}
	}
}
