package com.severine.api.danse.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {
	Iterable<Type> findAll(Sort sort);
	
	@Transactional
	@Modifying
	@Query("update Type set id_super_type = null where id_super_type=?1")
	public void deleteSuperTypeOfTypes(Long id); 
	
	List<Type> findAllBySuperType(SuperType idSuperType);
	

	@Query("select t from Type t where id_super_type in (?1)")
	List<Type> findAllTypeByIdSuperType(List<SuperType> superTypes);
}
