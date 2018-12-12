package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.TestFeignDTO;
import com.example.demo.service.TestFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class TestFeignController {

	@Autowired
	TestFeignService testService;

	@HystrixCommand(fallbackMethod = "defaultService")
	@GetMapping(value="/testFeign", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	public TestFeignDTO testFeign() {
		return new TestFeignDTO(testService.useFeignClient());
	}

	public TestFeignDTO defaultService() {
		return new TestFeignDTO(testService.useHystix());
	}
}
