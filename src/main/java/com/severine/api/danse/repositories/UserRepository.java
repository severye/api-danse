package com.severine.api.danse.repositories;

import org.springframework.data.repository.CrudRepository;

import com.severine.api.danse.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByEmail(String email);
	User findBySub(String sub);
}
