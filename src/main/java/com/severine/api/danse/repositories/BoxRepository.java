package com.severine.api.danse.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Box;

public interface BoxRepository extends CrudRepository<Box, Long> {
	Iterable<Box> findAll(Sort sort);
}
