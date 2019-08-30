package com.severine.api.danse.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.Book;
import com.severine.api.danse.entities.User;

public interface BookRepository extends CrudRepository<Book, Long>{

	@Transactional
	@Query("select sum(quantity) from Book where id_product=?1 and end_date is null")
	public Integer getQuantityBookedAndEndDateIsNull(Long idProduct);
	
	public List<Book> findAllByUserAndEndDateIsNull(User user);
	
}
