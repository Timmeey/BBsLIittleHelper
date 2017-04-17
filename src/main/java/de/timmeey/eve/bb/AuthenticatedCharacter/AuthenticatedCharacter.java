package de.timmeey.eve.bb.AuthenticatedCharacter;

import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 15.04.17.
 */
public interface AuthenticatedCharacter {

	String characterName();

	long characterId();

	AuthenticatedCharacter newRefreshToken(AccessToken token);

	AccessToken accessToken();

	String toString();
}
