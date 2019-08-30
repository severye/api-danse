package com.severine.api.danse.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Box;
import com.severine.api.danse.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

	@Transactional
	@Modifying
	@Query("update Product set id_colour = null where id_colour=?1")
	public void deleteColorOfProducts(Long id); 
	
	@Transactional
	@Modifying
	@Query("update Product set id_type = null where id_type=?1")
	public void deleteTypeOfProducts(Long id); 
	
	@Transactional
	@Modifying
	@Query("update Product set id_kind = null where id_kind=?1")
	public void deleteKindOfProducts(Long id); 
	
	@Transactional
	@Modifying
	@Query("update Product set id_category = null where id_category=?1")
	public void deleteCategoryOfProducts(Long id); 
	
	@Transactional
	@Modifying
	@Query("update Product set id_box = null where id_box=?1")
	public void deleteBoxOfProducts(Long id); 
	
	Iterable<Product> findAll(Sort sort);
	
	Iterable<Product> findAllByBox(Box box);
}
