package de.timmeey.eve.bb.AuthenticatedCharacter.fake;

import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacter;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacters;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import de.timmeey.eve.bb.OAuth2.OAuth2Api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by timmeey on 15.04.17.
 */
public class FakeAuthenticatedCharacters implements AuthenticatedCharacters {

	private final Map<Long, AuthenticatedCharacter> chars = new HashMap<>();
	private final OAuth2Api eveSSO;

	public FakeAuthenticatedCharacters(final OAuth2Api eveSSO) {
		this.eveSSO = eveSSO;
	}

	@Override
	public Optional<AuthenticatedCharacter> byId(final long id) {
		return Optional.ofNullable(chars.get(id));
	}

	@Override
	public AuthenticatedCharacter add(final long characterId, final String characterName, final AccessToken
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
