package com.severine.api.danse.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.severine.api.danse.entities.Kind;
import com.severine.api.danse.service.KindService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddName;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class KindController {
	@Autowired
	private KindService application;
	
	@RequestMapping(value = "/kinds/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteKind(@PathVariable Long id) {
		Reponse reponse = this.getKind(id);
		if (reponse.getStatus() != 0) {
			return reponse;
		}
		try {
			application.deleteKind(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@RequestMapping(value = "/kinds", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateKind(@RequestBody PostAddName post) {
		String name = post.getName();
		Long id = post.getId();
		Kind kind = new Kind();
		kind.setId(id);
		kind.setName(name);

		try {
			return new Reponse(0, application.addOrUpdateKind(kind));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/kinds", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
	public Reponse updateKind(@RequestBody PostAddName post) {
		if (post.getId() != null) {
			return addOrUpdateKind(post);
		}
		return new Reponse(1, "L'objet Genre n'a pas d'identifiant");
	}

	@RequestMapping(value = "/kinds", method = RequestMethod.GET)
	public Reponse getAllKinds(HttpServletResponse response) {
		try {
			return new Reponse(0, application.getAllKinds());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}
	
	public Reponse getKind(Long idKind) {
		Kind kind = null;
		try {
			kind = application.getKind(idKind);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(1, Static.getErreursForException(e1));
		}
		if (kind == null) {
			return new Reponse(2, null);
		}
		return new Reponse(0, kind);
	}


}
