package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.model.OAuthUserEntity;

public interface OAuthUserRepository extends JpaRepository<OAuthUserEntity, String>{

}
