package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.Box;

public interface BoxService {
	public List<Box> getAllBoxes();
	public Box getBox(Long idBox);
	public void deleteBox(Long id);
	public Box addOrUpdateBox(Box box);
	
	
}
