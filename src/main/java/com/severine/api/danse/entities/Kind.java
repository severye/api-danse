package com.severine.api.danse.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Kind {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	 
	private String name;
	
	public Kind() {
		
	}
	
	public Kind(String name) {
		this.name=name;
	}
	
	@Override
	public String toString() {
	    return String.format("Kind[id='%s', name='%s']",id, name);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
