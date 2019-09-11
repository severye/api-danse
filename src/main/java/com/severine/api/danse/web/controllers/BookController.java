package com.severine.api.danse.web.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.UserInfo;
import com.severine.api.danse.entities.Book;
import com.severine.api.danse.entities.User;
import com.severine.api.danse.service.BookService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddBook;
import com.severine.api.danse.web.models.Reponse;

@RestController
public class BookController {
	@Autowired
	private BookService application;
	
	@Value("auth0.client_id")
	private String clientId;
	
	@Value("auth0.client_secret")
	private String clientSecret;
	
	@Value("auth0.url")
	private String url;

	@RequestMapping(value = "/books", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addOrUpdateBook(@RequestBody PostAddBook post) {
		Integer quantity = post.getQuantity();
		Long idProduct = post.getIdProduct();
		User user;
		try {
			user = this.getUserConnected();
		} catch (Auth0Exception e1) {
			System.err.println(e1);
			return new Reponse(1, "un problème est survenu lors de la récupération de l'utilisateur");
		}

		Book book = new Book();
		book.setStartDate(new Timestamp(System.currentTimeMillis()));
		book.setQuantity(quantity);
		book.setUser(user);
		book.setIdProduct(idProduct);

		try {
			return new Reponse(0, application.addBook(book));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/books/user", method = RequestMethod.GET)
	public Reponse getAllbooksByUser() {
		try {
			User user = this.getUserConnected();
			return new Reponse(0, application.getAllBookByUser(user));
		} catch (Auth0Exception e1) {
			System.err.println(e1);
			return new Reponse(1, "un problème est survenu lors de la récupération des réservations");
		}
	}
	
	@RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
	public Reponse finishBook(@PathVariable Long id) {
		Book book = application.getBookById(id);
		book.setEndDate(new Timestamp(System.currentTimeMillis()));
		try {
			return new Reponse(0, application.addBook(book));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}
	public User getUserConnected() throws Auth0Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthAPI auth0 = new AuthAPI(url, clientId, clientSecret);

		UserInfo userInfo = auth0.userInfo((String) auth.getCredentials()).execute();
		String sub = (String) userInfo.getValues().get("sub");
		User user = null;
		user = application.getUserBySub(sub);
		if (user == null) {
			user = new User();
			user.setSub(sub);
			user.setName((String) userInfo.getValues().get("name"));
			application.createUser(user);
		}
		return user;
	}
	

}
