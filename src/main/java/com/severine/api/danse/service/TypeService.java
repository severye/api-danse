package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;

public interface TypeService {
	public List<Type> getAllTypes();
	public Type getType(Long idType);
	public Type addOrUpdateType(Type type);
	public void deleteType(Long id);
	public List<Type> getAllTypesOfSuperTypes(List<SuperType> superTypes);
	public List<Type> getAllTypesOfSuperType(SuperType superType);
}
