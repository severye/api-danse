package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.Category;
import com.severine.api.danse.repositories.CategoryRepository;
import com.severine.api.danse.repositories.ProductRepository;
import com.severine.api.danse.service.CategoryService;
import com.severine.api.shared.Utils;
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	

	@Override
	public List<Category> getAllCategories() {
		return Lists.newArrayList(categoryRepository.findAll(Utils.getSort()));
	}
	@Override
	public Category getCategory(Long idCategory) {
		return categoryRepository.findById(idCategory).get();
	}
	@Override
	public void deleteCategory(Long id) {
		productRepository.deleteCategoryOfProducts(id);
		categoryRepository.deleteById(id);
		
	}
	@Override
	public Category addOrUpdateCategory(Category category) {
		return categoryRepository.save(category);
	}
}
