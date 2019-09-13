package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.Box;
import com.severine.api.danse.repositories.BoxRepository;
import com.severine.api.danse.repositories.ProductRepository;
import com.severine.api.danse.service.BoxService;
import com.severine.api.danse.shared.Utils;
@Service
public class BoxServiceImpl implements BoxService {
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Box> getAllBoxes() {
		return Lists.newArrayList(boxRepository.findAll(Utils.getSort()));
	}
	
	@Override
	public Box getBox(Long idBox) {
		return boxRepository.findById(idBox).get();
	}
	
	@Override
	public void deleteBox(Long id) {
		productRepository.deleteBoxOfProducts(id);
		boxRepository.deleteById(id);
		
	}
	@Override
	public Box addOrUpdateBox(Box box) {
		return boxRepository.save(box);
	}
}
