package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.repositories.SuperTypeRepository;
import com.severine.api.danse.repositories.TypeRepository;
import com.severine.api.danse.service.SuperTypeService;
import com.severine.api.shared.Utils;

@Service
public class SuperTypeServiceImpl implements SuperTypeService {

	@Autowired
	public SuperTypeRepository superTypeRepository;
	@Autowired
	public TypeRepository typeRepository;
	@Override
	public List<SuperType> getAllSuperTypes() {
		return Lists.newArrayList(superTypeRepository.findAll(Utils.getSort()));
	}

	@Override
	public SuperType getSuperType(Long idSuperType) {
		return superTypeRepository.findById(idSuperType).get();
	}

	@Override
	public SuperType addOrUpdateSuperType(SuperType superType) {
		return superTypeRepository.save(superType);
	}

	@Override
	public void deleteSuperType(Long id) {
		typeRepository.deleteSuperTypeOfTypes(id);
		superTypeRepository.deleteById(id);
	}

}
