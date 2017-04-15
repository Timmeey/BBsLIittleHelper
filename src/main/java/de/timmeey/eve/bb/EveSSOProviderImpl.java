package de.timmeey.eve.bb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timmeey on 15.04.17.
 */
@EnableOAuth2Client
@Configuration
public class EveSSOProviderImpl implements EveSSOProvider {

	@Autowired
	AuthenticatedCharacters authenticatedCharacters;
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	private OAuth2RestTemplate eveTemplate;

	protected Filter ssoFilter() {
		this.eveTemplate = new OAuth2RestTemplate(eve(), oauth2ClientContext);
		OAuth2ClientAuthenticationProcessingFilter eveFilter = new OAuth2ClientAuthenticationProcessingFilter
				("/login");
		eveFilter.setRestTemplate(eveTemplate);
		UserInfoTokenServices tokenService = new UserInfoTokenServices(eveResource().getUserInfoUri(), eve()
				.getClientId());
		tokenService.setPrincipalExtractor(new PrincipalExtractor() {
			@Override
			public Object extractPrincipal(final Map<String, Object> map) {
				System.out.println(map);
				String characterID = String.valueOf(map.get("CharacterID"));
				String characterName = (String) map.get("CharacterName");
				AuthenticatedCharacter character = authenticatedCharacters.add(characterID, characterName,
						oauth2ClientContext.getAccessToken());
				return character;

			}
		});
		eveFilter.setTokenServices(tokenService);
		return eveFilter;
	}

	@Bean
	@ConfigurationProperties("eve.client")
	protected AuthorizationCodeResourceDetails eve() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("eve.resource")
	protected ResourceServerProperties eveResource() {
		return new ResourceServerProperties();
	}

	@Bean
	protected FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	@Override
	public OAuth2AccessToken newAccessToken(final AuthenticatedCharacter authenticatedCharacter, final
	OAuth2AccessToken acessToken) {
		try {
			Map<String, String> refreshRequest = new HashMap<>();
			refreshRequest.put("grant_type", "refresh_token");
			refreshRequest.put("refresh_token", acessToken.getRefreshToken().getValue());

			HttpHeaders requestHeaders = new HttpHeaders();
			String basicAuth = String.format("%s:%s", eve().getClientId(), eve().getClientSecret());
			String base64Enc = Base64.getEncoder().encodeToString(basicAuth.getBytes());
			requestHeaders.add("Authorization", String.format("Basic %s", base64Enc));
			requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<?> requestEntity = new HttpEntity<Object>(refreshRequest, requestHeaders);

			RestTemplate request = new RestTemplate();

			URI tokenUri = new URI(eve().getAccessTokenUri());
			//URI tokenUri = new URI("http://localhost:8081");

			ResponseEntity<OAuth2AccessToken> response = request.postForEntity(tokenUri, requestEntity,
					OAuth2AccessToken.class);
			System.out.println(response);
			return response.getBody();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.err.println("!!!!Someone fucked up. The accessTokenUri is invalid!!!!!");
			throw new RuntimeException("!!!!Someone fucked up. The accessTokenUri is invalid!!!!!", e);
		}


	}
}
