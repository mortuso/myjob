package com.example.demo.service;

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

}
