package com.example.auth.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	SecurityConfigProperties configProperties;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	/*
	 * OAuth2 resource client configuration
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
		.inMemory()
		.withClient(configProperties.getJwt().getClientId())
		.secret(passwordEncoder.encode(configProperties.getJwt().getClientSecret()))
		.authorizedGrantTypes(configProperties.getJwt().getGrantType())
		.authorities("OAUTH2_USER")
		.scopes(configProperties.getJwt().getScopeRead(), configProperties.getJwt().getScopeWrite())
		.accessTokenValiditySeconds(configProperties.getJwt().getTokenValiditySeconds())
		.resourceIds(configProperties.getJwt().getResourceIds());
	}
	
	/*
	 * Custom token response
	 */
	@Bean
	public TokenEnhancer customTokenEnancherAuth() {
		return (accessToken, authentication) -> {
			Map<String, Object> additionalInfo = new HashMap<>();
//			JwtUser user = (JwtUser) authentication.getPrincipal();
//			additionalInfo.put("password_expiring", user.isPasswordExpiring());
//	        additionalInfo.put("user_expiring", user.isUserExpiring());
	        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
	        return accessToken;
		};
	}
	
	
	/*
	 * JWT configuration
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnancherAuth(), accessTokenConverter));
		endpoints
		.tokenStore(tokenStore)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager);
//		.exceptionTranslator(exceptionTranslator());
	}
	
//	@Bean
//	public WebResponseExceptionTranslator exceptionTranslator() {
//		return new MesSecurityExceptionTranslator();
//	}

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
