package com.example.demo.interfaces;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.TestFeignConfiguration;
import com.example.demo.model.BookRest;

@FeignClient(name = "service2", configuration = TestFeignConfiguration.class)
public interface TestFeignInterface {
	
	@RequestMapping(method = RequestMethod.GET, value = "/service2/books")
	public Collection<BookRest> getBooks();
}