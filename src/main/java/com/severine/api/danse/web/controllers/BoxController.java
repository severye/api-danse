package com.severine.api.danse.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.severine.api.danse.entities.Box;
import com.severine.api.danse.service.BoxService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddName;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class BoxController {
	@Autowired
	private BoxService application;
	

	@RequestMapping(value = "/boxes", method = RequestMethod.GET)
	public Reponse getAllBoxes(HttpServletResponse response) {
		try {
			return new Reponse(0, application.getAllBoxes());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/boxes/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteBox(@PathVariable Long id) {
		Reponse reponse = this.getBox(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteBox(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@RequestMapping(value = "/boxes", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateBox(@RequestBody PostAddName post) {	
		Long id = post.getId();
		String name = post.getName();
		Box box = new Box();
		box.setId(id);
		box.setName(name);
		try {
			return new Reponse(0, application.addOrUpdateBox(box));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/boxes", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateBox(@RequestBody PostAddName post) {
		if (post.getId() != null) {
			return addOrUpdateBox(post);
		}
		return new Reponse(1, "L'objet Malle n'a pas d'identifiant");
	}

	public Reponse getBox(long idBox) {
		Box box = null;
		try {
			box = application.getBox(idBox);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (box == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, box);
	}

}
