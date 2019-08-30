package com.severine.api.danse.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Size;

public interface SizeRepository extends CrudRepository<Size, Long> {
	Iterable<Size> findAll(Sort sort);
}
