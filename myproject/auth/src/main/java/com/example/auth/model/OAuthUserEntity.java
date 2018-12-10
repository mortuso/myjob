package com.example.auth.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the oauth_user database table.
 * 
 */
@Entity
@Table(name="oauth_users")
//@NamedQuery(name="OAuthUserEntity.findAll", query="SELECT o FROM OAuthUserEntity o")
@Getter @Setter @NoArgsConstructor
public class OAuthUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String password;

	@Id
	private String username;
}