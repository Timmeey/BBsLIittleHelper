package de.timmeey.eve.bb;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Created by timmeey on 16.04.17.
 */
public interface EveSSOProvider {

	OAuth2AccessToken newAccessToken(AuthenticatedCharacter authenticatedCharacter, OAuth2AccessToken acessToken);
}
