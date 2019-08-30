package com.severine.api.danse.web.models;

import java.util.List;

import com.severine.api.danse.entities.Box;
import com.severine.api.danse.entities.Category;
import com.severine.api.danse.entities.Colour;
import com.severine.api.danse.entities.Kind;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;

public class PostSearchProducts {

	List<Colour>colours;
	List<Category>categories;
	List<Kind>kinds;
	List<Type>types;
	List<Box>boxes;
	List<SuperType>superTypes;
	Integer quantity;
	String name;
	Boolean available;
	
	public List<SuperType> getSuperTypes() {
		return superTypes;
	}
	public void setSuperTypes(List<SuperType> superTypes) {
		this.superTypes = superTypes;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<Colour> getColours() {
		return colours;
	}
	public void setColours(List<Colour> colours) {
		this.colours = colours;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public List<Kind> getKinds() {
		return kinds;
	}
	public void setKinds(List<Kind> kinds) {
		this.kinds = kinds;
	}
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.types = types;
	}
	public List<Box> getBoxes() {
		return boxes;
	}
	public void setBoxes(List<Box> boxes) {
		this.boxes = boxes;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
