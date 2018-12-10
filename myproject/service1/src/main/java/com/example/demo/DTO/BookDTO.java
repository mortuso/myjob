package com.example.demo.DTO;

import java.util.Collection;

import com.example.demo.model.BookRest;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDTO {
	
	private Collection<BookRest> books;
	
	public BookDTO(Collection<BookRest> collection) {
		books.addAll(collection);
	}
}
