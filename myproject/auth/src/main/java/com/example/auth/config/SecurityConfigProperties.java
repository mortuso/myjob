package com.example.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "security") 
@Getter @Setter
public class SecurityConfigProperties {
	
	@Getter @Setter
	public static class Jwt{
	
	    private String resourceIds;
		private String signingKey;
		private String clientId;
		private String clientSecret;
		private String grantType;
		private String scopeRead;
		private String scopeWrite;
		private int tokenValiditySeconds;
	}

	private Jwt jwt;
	private String securityRealm;
}
