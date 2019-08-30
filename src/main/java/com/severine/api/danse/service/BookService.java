package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.Book;
import com.severine.api.danse.entities.User;

public interface BookService {

	public Book addBook(Book book);
	public List<Book> getAllBookByUser(User user);
	public Book getBookById(Long id);
	public User getUserBySub(String sub);
	public User createUser(User user);
}
