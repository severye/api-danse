package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.SizeQuantity;

public interface SizeQuantityService {

	public List<SizeQuantity> getAllSizeQuantities();	
	public SizeQuantity getSizeQuantity(Long idProduct, Long idSize);	
	public void deleteSizeQuantity(Long idProduct, Long idSize);
	public SizeQuantity addOrUpdateSizeQuantity(SizeQuantity sizeQuantity);
	
}
