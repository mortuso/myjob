package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.FeignInterface;
import com.example.demo.model.BookRest;

@Service
public class TestService {
	
	@Autowired
	FeignInterface feignInterface;
	
	
	public Collection<BookRest> useFeignClient() {
		return feignInterface.getBooks();
	}

}
