package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import feign.RequestInterceptor;

public class TestFeignConfiguration {

	@Autowired
	SecurityConfigProperties configProperties;

	
	@Bean
	RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
	}


	private OAuth2ProtectedResourceDetails resource() {

		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setUsername("test");
		resourceDetails.setPassword("test");
		resourceDetails.setAccessTokenUri("http://localhost:8083/auth/oauth/token");
		resourceDetails.setClientId(configProperties.getJwt().getClientId());
		resourceDetails.setClientSecret(configProperties.getJwt().getClientSecret());
		resourceDetails.setGrantType(configProperties.getJwt().getGrantType());
		resourceDetails.setScope(Arrays.asList(configProperties.getJwt().getScopeWrite()));
		return resourceDetails;
	}


}