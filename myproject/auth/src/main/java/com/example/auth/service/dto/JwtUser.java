package com.example.auth.service.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final String username;
	private final String password;
	
	public JwtUser(JwtUserCredential credential) {
		this.username = credential.getUsername();
		this.password = credential.getPassword();
			}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return isEnabled();
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}