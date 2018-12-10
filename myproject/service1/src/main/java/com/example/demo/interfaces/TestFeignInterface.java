package com.example.demo.interfaces;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.BookRest;

@FeignClient("service2")
public interface TestFeignInterface {
	
	@RequestMapping(method = RequestMethod.GET, value = "/service2/books")
	public Collection<BookRest> getBooks();
}