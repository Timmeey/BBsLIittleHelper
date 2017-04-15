package de.timmeey.eve.bb;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Created by timmeey on 15.04.17.
 */
public interface AuthenticatedCharacter extends AuthenticatedPrincipal {

	String characterName();

	String characterId();

	String accessToken();

	AuthenticatedCharacter newRefreshToken(OAuth2AccessToken token);

	@Override
	default String getName() {
		return this.characterId();
	}


	String toString();
}
