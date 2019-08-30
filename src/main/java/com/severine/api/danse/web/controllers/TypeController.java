package com.severine.api.danse.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.exception.Auth0Exception;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;
import com.severine.api.danse.service.SuperTypeService;
import com.severine.api.danse.service.TypeService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddType;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class TypeController {
	@Autowired
	private TypeService application;
	@Autowired
	private SuperTypeService superTypeService;

	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public Reponse getAllTypes(HttpServletResponse response) throws Auth0Exception {
		try {
			return new Reponse(0, application.getAllTypes());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}
	
	@RequestMapping(value = "/types/supertypes", method = RequestMethod.POST)
	public Reponse getAllTypesOfSuperTypes(@RequestBody List<SuperType> superTypes) throws Auth0Exception {
		try {
			return new Reponse(0, application.getAllTypesOfSuperTypes(superTypes));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/types/supertypes/{id}", method = RequestMethod.GET)
	public Reponse getAllTypesOfSuperType(@PathVariable Long id) throws Auth0Exception {
		try {
			SuperType superType = superTypeService.getSuperType(id);
			return new Reponse(0, application.getAllTypesOfSuperType(superType));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}
	@RequestMapping(value = "/types", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateType(@RequestBody PostAddType post) {
		String name = post.getName();
		Long id = post.getId();
		Long idSuperType = post.getIdSuperType();
		Type type = new Type();
		type.setId(id);
		type.setName(name);
		SuperType superType = superTypeService.getSuperType(idSuperType);
		type.setSuperType(superType);
		try {
			return new Reponse(0, application.addOrUpdateType(type));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/types", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateType(@RequestBody PostAddType post) {
		if (post.getId() != null) {
			return addOrUpdateType(post);
		}
		return new Reponse(1, "L'objet type n'a pas d'identifiant");
	}

	@RequestMapping(value = "/types/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteType(@PathVariable Long id) {
		Reponse reponse = this.getType(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteType(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}
	
	public Reponse getType(Long idType) {
		Type type = null;
		try {
			type = application.getType(idType);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (type == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, type);
	}
}
