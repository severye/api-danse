package com.severine.api.danse.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.severine.api.danse.entities.Colour;
import com.severine.api.danse.service.ColourService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddColour;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class ColorController {
	@Autowired
	private ColourService application;

	@RequestMapping(value = "/colours/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteColour(@PathVariable Long id) {
		Reponse reponse = this.getColour(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteColour(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@RequestMapping(value = "/colours", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateColour(@RequestBody PostAddColour post) {
		Long id = post.getId();
		String name = post.getName();
		String codeColour = post.getCode();
		Colour colour = new Colour();
		colour.setColorCode(codeColour);
		colour.setId(id);
		colour.setName(name);
		try {
			return new Reponse(0, application.addOrUpdateColour(colour));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/colours", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateColour(@RequestBody PostAddColour post) {
		if (post.getId() != null) {
			return addOrUpdateColour(post);
		}
		return new Reponse(1, "L'objet Couleur n'a pas d'identifiant");
	}

	@RequestMapping(value = "/colours", method = RequestMethod.GET)
	public Reponse getAllColours(HttpServletResponse response) {
		try {
			return new Reponse(0, application.getAllColours());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}	
	public Reponse getColour(Long idColour) {
		Colour colour = null;
		try {
			colour = application.getColour(idColour);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (colour == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, colour);
	}

}
