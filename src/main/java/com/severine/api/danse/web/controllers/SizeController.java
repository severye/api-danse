package com.severine.api.danse.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.severine.api.danse.entities.Size;
import com.severine.api.danse.service.SizeService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddName;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class SizeController {
	
	@Autowired
	private SizeService application;

	@RequestMapping(value = "/sizes", method = RequestMethod.GET)
	public Reponse getAllSizes(HttpServletResponse response) {
		try {
			return new Reponse(0, application.getAllSizes());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/sizes/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteSize(@PathVariable Long id) {
		Reponse reponse = getSize(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteSize(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@RequestMapping(value = "/sizes", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateSize(@RequestBody PostAddName post) {
		Long id = post.getId();
		String name = post.getName();
		Size size = new Size();
		size.setId(id);
		size.setName(name);
		try {
			return new Reponse(0, application.addOrUpdateSize(size));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/sizes", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateSize(@RequestBody PostAddName post) {
		if (post.getId() != null) {
			return addOrUpdateSize(post);
		}
		return new Reponse(1, "L'objet Taille n'a pas d'identifiant");
	}
	
	public Reponse getSize(Long idSize) {
		Size size = null;
		try {
			size = application.getSize(idSize);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (size == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, size);
	}
	
	
}
