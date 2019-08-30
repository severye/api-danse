package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.Size;

public interface SizeService {
	public List<Size> getAllSizes();
	public Size getSize(Long idSize);
	public void deleteSize(Long id);
	public Size addOrUpdateSize(Size size);

	
}
