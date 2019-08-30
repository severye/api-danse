package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.Size;
import com.severine.api.danse.repositories.SizeQuantityRepository;
import com.severine.api.danse.repositories.SizeRepository;
import com.severine.api.danse.service.SizeService;
import com.severine.api.shared.Utils;

@Service
public class SizeServiceImpl implements SizeService {

	@Autowired
	private SizeRepository sizeRepository;
	@Autowired
	private SizeQuantityRepository sizeQuantityRepository;

	@Override
	public List<Size> getAllSizes() {
		return Lists.newArrayList(sizeRepository.findAll(Utils.getSort()));
	}

	@Override
	public Size getSize(Long idSize) {
		return sizeRepository.findById(idSize).get();
	}
	
	@Override
	public void deleteSize(Long id) {
		sizeQuantityRepository.deleteSizeOfsizeQuantityRepository(id);
		sizeRepository.deleteById(id);		
	}

	@Override
	public Size addOrUpdateSize(Size size) {
		return sizeRepository.save(size);
	}
	
	
}
