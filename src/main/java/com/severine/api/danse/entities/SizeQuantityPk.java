package com.severine.api.danse.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SizeQuantityPk implements Serializable{
	private static final long serialVersionUID = 1L;
	Long idProduct;
	Long idSize;
	
}
