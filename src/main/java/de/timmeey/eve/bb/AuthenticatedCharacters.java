package de.timmeey.eve.bb;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Optional;

/**
 * Created by timmeey on 15.04.17.
 */
public interface AuthenticatedCharacters extends Iterable<AuthenticatedCharacter> {

	Optional<AuthenticatedCharacter> byId(String id);

	AuthenticatedCharacter add(String characterId, String characterName, OAuth2AccessToken authToken);


}
