package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.SuperType;

public interface SuperTypeService {

	public List<SuperType> getAllSuperTypes();
	public SuperType getSuperType(Long idSuperType);
	public SuperType addOrUpdateSuperType(SuperType superType);
	public void deleteSuperType(Long id);
}
