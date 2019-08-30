package com.severine.api.danse.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Kind;

public interface KindRepository extends CrudRepository<Kind, Long> {
	Iterable<Kind> findAll(Sort sort);
}
