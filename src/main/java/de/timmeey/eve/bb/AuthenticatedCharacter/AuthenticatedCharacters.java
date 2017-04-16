package de.timmeey.eve.bb.AuthenticatedCharacter;

import de.timmeey.eve.bb.OAuth2.AccessToken;

import java.util.Optional;

/**
 * Created by timmeey on 15.04.17.
 */
public interface AuthenticatedCharacters extends Iterable<AuthenticatedCharacter> {

	Optional<AuthenticatedCharacter> byId(long id);

	AuthenticatedCharacter add(long characterId, String characterName, AccessToken accessToken);


}
