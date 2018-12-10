package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.BookDTO;
import com.example.demo.service.TestService;

@RestController
public class TestController {
	
	@Autowired
	TestService testService;
	
	@GetMapping(value="/testFeign", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	 public BookDTO testFeign() {
		 return new BookDTO(testService.useFeignClient());
	 }
	
	@GetMapping(value="/up", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	 public String test() {
		 return "contoller ok";
	 }
}
