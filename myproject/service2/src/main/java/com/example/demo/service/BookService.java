package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.example.demo.model.Book;

@Service
public class BookService {
	
	public Collection<Book> getBooks(){
		
		Book book1 = new Book();
		book1.setAuthor("Dante Alighieri");
		book1.setId(1);
		book1.setTitle("la divina commedia");
		
		Book book2 = new Book();
		book2.setAuthor("Baeldung");
		book2.setId(2);
		book2.setTitle("Spring cloud reference");
		
		Collection<Book> books = new ArrayList<>();
		books.addAll(Arrays.asList(book1, book2));
		return books;
	}
	
public Collection<Book> getBooksAlternative(){
		
		Book book1 = new Book();
		book1.setAuthor("Alessandro Manzoni");
		book1.setId(1);
		book1.setTitle("i promessi sposi");
		
		Book book2 = new Book();
		book2.setAuthor("Ernest Hemingway");
		book2.setId(2);
		book2.setTitle("il vecchio e il mare");
		
		Collection<Book> books = new ArrayList<>();
		books.addAll(Arrays.asList(book1, book2));
		return books;
	}

}
