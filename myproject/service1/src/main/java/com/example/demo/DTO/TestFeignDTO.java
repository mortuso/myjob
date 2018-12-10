package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.Collection;

import com.example.demo.model.BookRest;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TestFeignDTO {
	
	private Collection<BookRest> books;
	
	public TestFeignDTO(Collection<BookRest> collection) {
		books = new ArrayList<>();
		books.addAll(collection);
	}
}
