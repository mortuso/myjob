package com.example.auth.controller;

import java.security.Principal;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.service.UserDetailsServiceImpl;
import com.example.auth.service.dto.ChangeStationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/users")
@Log
@Api(value = "User controller")
public class UserController {
	
	@Autowired
	private UserDetailsServiceImpl service;
	
	@ApiOperation(value = "Change Station")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	
	@PostMapping(value="/changeStation", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	public OAuth2AccessToken changeStation(
			@ApiParam(value = "User param for change station")
			@RequestBody ChangeStationDTO request, Principal principal, Authentication authentication, OAuth2Authentication oAuth2Authentication)
	{
		log.log(Level.FINE, "Starting change station for user {0}", principal.getName());
		return service.changeStation(request, oAuth2Authentication, principal, authentication.getAuthorities());	
	}
	
}
