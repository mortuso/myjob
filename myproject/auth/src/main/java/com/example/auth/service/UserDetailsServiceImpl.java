package com.example.auth.service;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import com.example.auth.config.SecurityConfigProperties;
import com.example.auth.model.OAuthUserEntity;
import com.example.auth.repository.OAuthUserRepository;
import com.example.auth.service.dto.ChangeStationDTO;
import com.example.auth.service.dto.JwtUser;
import com.example.auth.service.dto.JwtUserCredential;

import lombok.extern.java.Log;


@Service
@Log
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private OAuthUserRepository oAuthUserRepository;

	@Autowired
	private SecurityConfigProperties configProperties;

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;


	@Override
	public UserDetails loadUserByUsername(String username) {
		log.fine("LoadUserByUsername");

		Optional<OAuthUserEntity> userOpt = oAuthUserRepository.findById(username);

		if (userOpt.isPresent()) {
			log.fine("User found");
			
			OAuthUserEntity user = userOpt.get();
			
		return new JwtUser(new JwtUserCredential(user.getUsername(), user.getPassword()));
		} else {
			throw new IllegalArgumentException(username + " is user not found");
		}
	}



	@Transactional
	public OAuth2AccessToken changeStation(ChangeStationDTO changeStationDTO, OAuth2Authentication oAuth2Authentication,
			Principal principal, Collection<? extends GrantedAuthority> authorities) {

		Optional<OAuthUserEntity> userOpt = oAuthUserRepository.findById(principal.getName());
		log.fine("Found user on DB");
		
		if(!userOpt.isPresent()) {
			throw new IllegalArgumentException(principal.getName() + " not found");
		}
		
		OAuthUserEntity user = userOpt.get();

		oAuthUserRepository.saveAndFlush(user);

		return createNewToken(oAuth2Authentication, authorities, principal);

	}

	/**
	 * Create new token
	 * 
	 * @param oAuth2Authentication
	 * @param oauthGroups
	 * @param authorities
	 * @param principal
	 * @param userPlant
	 * @return new token
	 */
	private OAuth2AccessToken createNewToken(OAuth2Authentication oAuth2Authentication, 
			Collection<? extends GrantedAuthority> authorities, Principal principal) {

		log.fine("Creating new token");

		// set token validity seconds
		tokenServices.setAccessTokenValiditySeconds(configProperties.getJwt().getTokenValiditySeconds());

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		// set privileges from old token
		for (GrantedAuthority auth : authorities) {
			grantedAuthorities.add(auth);
		}

//		// set privileges in authorities
//		Set<GrantedAuthority> privileges = new HashSet<>();
//		for (OAuthGroupEntity OAuthGroupEntity : oauthGroups) {
//			// filter for Plant
//			if (OAuthGroupEntity.getPlantId().equals(userPlantId)) {
//				for (PrivilegeEntity PrivilegeEntity : OAuthGroupEntity.getPrivileges()) {
//					privileges.add(new SimpleGrantedAuthority(PrivilegeEntity.getPrivilegeId()));
//				}
//			}
//		}
//		grantedAuthorities.addAll(privileges);

		// create OAuth2Request based on login request
		OAuth2Request oauth2Request = new OAuth2Request(new HashMap<String, String>(),
				oAuth2Authentication.getOAuth2Request().getClientId(), grantedAuthorities, true,
				oAuth2Authentication.getOAuth2Request().getScope(),
				oAuth2Authentication.getOAuth2Request().getResourceIds(),
				oAuth2Authentication.getOAuth2Request().getRedirectUri(),
				oAuth2Authentication.getOAuth2Request().getResponseTypes(),
				oAuth2Authentication.getOAuth2Request().getExtensions());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,
				null, grantedAuthorities);

		// create new OAuth2Authentication
		OAuth2Authentication newOAuth2Authentication = new OAuth2Authentication(oauth2Request, authenticationToken);

		OAuth2AccessToken token = tokenServices.createAccessToken(newOAuth2Authentication);

		// add custom claim
//		Map<String, Object> additionalInfo = new HashMap<>();
//		if (userPlantId != null) {
//			additionalInfo.put("plant", userPlantId);
//		}
//		if (stationId != null) {
//			additionalInfo.put("station", stationId);
//		}
//
//		if (token != null) {
//			((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionalInfo);
//		}
		// create and return new token
		return accessTokenConverter.enhance(token, newOAuth2Authentication);
	}

}
