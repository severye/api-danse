package com.severine.api.danse.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
	  Iterable<Category> findAll(Sort sort);

}
