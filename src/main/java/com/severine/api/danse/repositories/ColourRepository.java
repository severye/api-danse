package com.severine.api.danse.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Colour;

public interface ColourRepository extends CrudRepository<Colour, Long>{
	Iterable<Colour> findAll(Sort sort);
}
