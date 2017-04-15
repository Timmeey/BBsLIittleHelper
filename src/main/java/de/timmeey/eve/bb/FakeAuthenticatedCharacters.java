package de.timmeey.eve.bb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by timmeey on 15.04.17.
 */
@Repository
public class FakeAuthenticatedCharacters implements AuthenticatedCharacters {

	Map<String, AuthenticatedCharacter> chars = new HashMap<>();
	@Autowired
	private EveSSOProvider eveSSO;

	@Override
	public Optional<AuthenticatedCharacter> byId(final String id) {
		return Optional.ofNullable(chars.get(id));
	}

	@Override
	public AuthenticatedCharacter add(final String characterId, final String characterName, final OAuth2AccessToken
			authToken) {
		final Optional<AuthenticatedCharacter> authenticatedCharacter = byId(characterId);

		if (authenticatedCharacter.isPresent()) {
			return authenticatedCharacter.get().newRefreshToken(authToken);
		} else {
			AuthenticatedCharacter authChar = new FakeAuthenticatedCharacter(characterName, characterId, authToken,
					eveSSO);
			this.chars.put(authChar.characterId(), authChar);
			return authChar;
		}

	}

	@Override
	public Iterator<AuthenticatedCharacter> iterator() {
		return null;
	}
}
