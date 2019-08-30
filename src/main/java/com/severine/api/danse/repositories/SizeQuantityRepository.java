package com.severine.api.danse.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.SizeQuantity;

public interface SizeQuantityRepository extends CrudRepository<SizeQuantity, Long>{

	@Transactional
	@Modifying
	@Query("delete from size_quantity where id_product=?1 and id_size=?2")
	public void deleteSizeQuantity(Long idProduct, Long idSize);

	@Query("select sq from size_quantity sq where id_product=?1 and id_size=?2")
	public SizeQuantity getSizeQuantity(Long idProduct, Long idSize);
	
	Iterable<SizeQuantity> findAll(Sort sort);

	@Transactional
	@Modifying
	@Query("delete from size_quantity where id_size=?1")
	public void deleteSizeOfsizeQuantityRepository(Long id);
	
	@Transactional
	@Modifying
	@Query("delete from size_quantity where id_product=?1")
	public void deleteAllSizeQuantityByIdProduct(Long id);
}
