package com.severine.api.danse.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.severine.api.danse.entities.Category;
import com.severine.api.danse.service.CategoryService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddName;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService application;
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public Reponse getAllCategories(HttpServletResponse response) {
		try {
			return new Reponse(0, application.getAllCategories());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteCategory(@PathVariable Long id) {
		Reponse reponse = this.getCategory(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteCategory(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@RequestMapping(value = "/categories", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateCategorie(@RequestBody PostAddName post) {
		Long id = post.getId();
		String name = post.getName();
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		try {
			return new Reponse(0, application.addOrUpdateCategory(category));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/categories", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateCategory(@RequestBody PostAddName post) {
		if (post.getId() != null) {
			return addOrUpdateCategorie(post);
		}
		return new Reponse(1, "L'objet Categorie n'a pas d'identifiant");
	}
	public Reponse getCategory(Long idCategory) {
		Category category = null;
		try {
			category = application.getCategory(idCategory);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (category == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, category);
	}
}
