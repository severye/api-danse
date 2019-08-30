package com.severine.api.danse.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.SuperType;

public interface SuperTypeRepository extends CrudRepository<SuperType, Long> { 
	Iterable<SuperType> findAll(Sort sort);
}
