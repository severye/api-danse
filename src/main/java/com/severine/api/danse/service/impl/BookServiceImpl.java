package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.severine.api.danse.entities.Book;
import com.severine.api.danse.entities.User;
import com.severine.api.danse.repositories.BookRepository;
import com.severine.api.danse.repositories.UserRepository;
import com.severine.api.danse.service.BookService;
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}
	@Override
	public User getUserBySub(String sub) {
		return userRepository.findBySub(sub);
	}
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public List<Book> getAllBookByUser(User user) {
		return bookRepository.findAllByUserAndEndDateIsNull(user);
	}
	
	@Override
	public Book getBookById(Long id) {
		return bookRepository.findById(id).get();
	}
}
