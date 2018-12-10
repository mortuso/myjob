package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private SecurityConfigProperties configProperties;
	
    @Autowired
    private ResourceServerTokenServices tokenServices;
    
//    @Autowired 
//    private WebResponseExceptionTranslator exceptionTranslator;
	
    /*
     * Resource server oauth configuration
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
        .resourceId(configProperties.getJwt().getResourceIds())
        .tokenServices(tokenServices)
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint());
    }

    /*
     * Security configuration
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        	.antMatchers("/separators/**").permitAll()
			.antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html*", "/webjars/**").permitAll() // swagger
        	.anyRequest().authenticated();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        final OAuth2AccessDeniedHandler handler = new OAuth2AccessDeniedHandler();
//        handler.setExceptionTranslator(exceptionTranslator);
        return handler;
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        final OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
//        entryPoint.setExceptionTranslator(exceptionTranslator);
        return entryPoint;
    }

}