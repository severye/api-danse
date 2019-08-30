package com.severine.api.danse.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.exception.Auth0Exception;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.service.SuperTypeService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddName;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class SuperTypeController {
	@Autowired
	private SuperTypeService application;

	@RequestMapping(value = "/supertypes", method = RequestMethod.GET)
	public Reponse getAllSuperTypes(HttpServletResponse response) throws Auth0Exception {
		try {
			return new Reponse(0, application.getAllSuperTypes());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/supertypes", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateSuperType(@RequestBody PostAddName post) {
		String name = post.getName();
		Long id = post.getId();
		SuperType supertype = new SuperType();
		supertype.setId(id);
		supertype.setName(name);
		try {
			return new Reponse(0, application.addOrUpdateSuperType(supertype));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/supertypes", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateType(@RequestBody PostAddName post) {
		if (post.getId() != null) {
			return addOrUpdateSuperType(post);
		}
		return new Reponse(1, "L'objet type n'a pas d'identifiant");
	}

	@RequestMapping(value = "/supertypes/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteType(@PathVariable Long id) {
		Reponse reponse = this.getSuperType(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteSuperType(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}
	
	public Reponse getSuperType(Long idSuperType) {
		SuperType supertype = null;
		try {
			supertype = application.getSuperType(idSuperType);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (supertype == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, supertype);
	}
}
