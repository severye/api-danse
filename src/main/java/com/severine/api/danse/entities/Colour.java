package com.severine.api.danse.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Colour {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	 
	private String name;
	private String colorCode;
	
	public Colour() {
		
	}
	
	public Colour(String name, String colorCode) {
		this.name=name;
		this.colorCode = colorCode;
	}
	
	@Override
	public String toString() {
	    return String.format("Colour[id='%s', name='%s',code='%s']",id, name,colorCode);
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

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	
	
}
