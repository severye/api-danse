package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.Colour;

public interface ColourService {

	public List<Colour> getAllColours();
	public Colour getColour(Long idColour);
	public void deleteColour(Long id);
	public Colour addOrUpdateColour(Colour colour);
	
	
}
