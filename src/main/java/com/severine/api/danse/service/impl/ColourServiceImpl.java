package com.severine.api.danse.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.Colour;
import com.severine.api.danse.repositories.ColourRepository;
import com.severine.api.danse.repositories.ProductRepository;
import com.severine.api.danse.service.ColourService;
import com.severine.api.danse.shared.Utils;
@Service
public class ColourServiceImpl implements ColourService {

	@Autowired
	private ColourRepository colourRepository;
	@Autowired
	private ProductRepository productRepository;
	

	@Override
	public List<Colour> getAllColours() {
		return Lists.newArrayList(colourRepository.findAll(Utils.getSort()));
	}
	@Override
	public Colour getColour(Long idColour) {
		Optional<Colour> result = colourRepository.findById(idColour);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	@Override
	public void deleteColour(Long id) {
		productRepository.deleteColorOfProducts(id);
		colourRepository.deleteById(id);
		
	}
	@Override
	public Colour addOrUpdateColour(Colour colour) {
		return colourRepository.save(colour);
	}
}
