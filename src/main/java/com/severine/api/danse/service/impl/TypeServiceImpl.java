package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;
import com.severine.api.danse.repositories.ProductRepository;
import com.severine.api.danse.repositories.TypeRepository;
import com.severine.api.danse.service.TypeService;
import com.severine.api.danse.shared.Utils;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Type addOrUpdateType(Type type) {
		return typeRepository.save(type);
	}
	
	@Override
	public List<Type> getAllTypes() {
		return Lists.newArrayList(typeRepository.findAll(Utils.getSort()));
	}	
	
	@Override
	public Type getType(Long idType) {
		return typeRepository.findById(idType).get();
	}
	@Override
	public void deleteType(Long id) {
		productRepository.deleteTypeOfProducts(id);
		typeRepository.deleteById(id);
		
	}

	@Override
	public List<Type> getAllTypesOfSuperTypes(List<SuperType> superTypes) {
		return typeRepository.findAllTypeByIdSuperType(superTypes);
	}

	@Override
	public List<Type> getAllTypesOfSuperType(SuperType superType) {
		return typeRepository.findAllBySuperType(superType);
	}
	
	
}
