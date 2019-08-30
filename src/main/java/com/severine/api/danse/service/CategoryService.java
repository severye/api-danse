package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.Category;

public interface CategoryService {
	public List<Category> getAllCategories();
	public Category getCategory(Long idCategory);
	public void deleteCategory(Long id);
	public Category addOrUpdateCategory(Category category);

	
}
