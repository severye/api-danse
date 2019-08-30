package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.SizeQuantity;
import com.severine.api.danse.repositories.SizeQuantityRepository;
import com.severine.api.danse.service.SizeQuantityService;

@Service
public class SizeQuantityServiceImpl implements SizeQuantityService{
	
	@Autowired
	private SizeQuantityRepository sizeQuantityRepository;
	
	@Override
	public List<SizeQuantity> getAllSizeQuantities() {
		return Lists.newArrayList(sizeQuantityRepository.findAll());
	}
	@Override
	public void deleteSizeQuantity(Long idProduct, Long idSize) {
		sizeQuantityRepository.deleteSizeQuantity(idProduct, idSize);
		
	}
	@Override
	public SizeQuantity addOrUpdateSizeQuantity(SizeQuantity sizeQuantity) {
		return sizeQuantityRepository.save(sizeQuantity);
	}
	@Override
	public SizeQuantity getSizeQuantity(Long idProduct, Long idSize) {
		return sizeQuantityRepository.getSizeQuantity(idProduct, idSize);
	}
}
