package de.timmeey.eve.bb.OAuth2;

import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacter;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacters;

import java.io.IOException;

/**
 * Created by timmeey on 16.04.17.
 */
public interface EveOAuth2Api extends OAuth2Api {

	/**
	 * Obtains the character this accessToken belongs to
	 *
	 * @param accessToken the accessToken
	 * @return the authenticatedCharacter for this token
	 */
	AuthenticatedCharacter obtainCharacter(AccessToken accessToken, AuthenticatedCharacters authenticatedCharacters)
			throws IOException;
}
