package com.example.auth.controller.response;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class PrivilegesResponse {
	private Set<String> privileges;
	
	public PrivilegesResponse(Collection<? extends GrantedAuthority> authorities) {
		
		privileges = new TreeSet<>();
		for(GrantedAuthority authority : authorities) {
			privileges.add(authority.getAuthority());
		}
	}
}
