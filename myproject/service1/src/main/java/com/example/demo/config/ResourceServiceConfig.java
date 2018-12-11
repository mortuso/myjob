package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	SecurityConfigProperties configProperties;

	/*
     * Resource server oauth configuration
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
        .resourceId(configProperties.getJwt().getResourceIds())
        .tokenServices(tokenServices())
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint());
    }

    /*
     * Security configuration
     */
    @Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html*", "/webjars/**").permitAll() // swagger
			.anyRequest().authenticated();
	}
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        final OAuth2AccessDeniedHandler handler = new OAuth2AccessDeniedHandler();
//        handler.setExceptionTranslator(exceptionTranslator());
        return handler;
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        final OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
//        entryPoint.setExceptionTranslator(exceptionTranslator());
        return entryPoint;
    }
    

	
//	@Bean
//	public WebResponseExceptionTranslator exceptionTranslator() {
//		return new MesSecurityExceptionTranslator();
//	}
//	
	/*
     * JTW token store configuration
     */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	/*
     * JTW access token converter
     */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(configProperties.getJwt().getSigningKey());
		return converter;
	}

	/*
     * JTW token service configuration
     */
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(false);
		return defaultTokenServices;
	}

}