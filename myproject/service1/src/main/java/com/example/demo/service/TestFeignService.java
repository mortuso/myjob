package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.TestFeignInterface;
import com.example.demo.model.BookRest;

@Service
public class TestFeignService {

	@Autowired
	TestFeignInterface feignInterface;


	public Collection<BookRest> useFeignClient() {
		Collection<BookRest> bookRests = feignInterface.getBooks();
		return bookRests;
	}


	public Collection<BookRest> useHystix() {
		BookRest book1 = new BookRest();
		book1.setAuthor("Hystrix Author");
		book1.setId(1);
		book1.setTitle("Hystrix Book");

		Collection<BookRest> books = new ArrayList<>();
		books.addAll(Arrays.asList(book1));
		return books;
	}

}
