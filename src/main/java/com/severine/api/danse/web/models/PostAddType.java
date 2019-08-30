package com.severine.api.danse.web.models;

public class PostAddType {

	private Long id;
	private String name;
	private Long idSuperType;
	
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

	public Long getIdSuperType() {
		return idSuperType;
	}

	public void setIdSuperType(Long idSuperType) {
		this.idSuperType = idSuperType;
	}
	
}
