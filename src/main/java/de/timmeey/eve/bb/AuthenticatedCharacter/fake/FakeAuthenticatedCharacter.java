package de.timmeey.eve.bb.AuthenticatedCharacter.fake;

import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacter;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import de.timmeey.eve.bb.OAuth2.OAuth2Api;

/**
 * Created by timmeey on 15.04.17.
 */
public class FakeAuthenticatedCharacter implements AuthenticatedCharacter {
	private final String characterName;
	private final long characterID;
	private final OAuth2Api eveSSOProvider;
	private AccessToken oAuth2AccessToken;

	protected FakeAuthenticatedCharacter(final String characterName, final long characterID, AccessToken
			accessToken, final OAuth2Api eveSSO) {
		this.characterName = characterName;

		this.characterID = characterID;
		this.newRefreshToken(accessToken);
		this.eveSSOProvider = eveSSO;
	}

	@Override
	public String characterName() {
		return this.characterName;
	}

	@Override
	public long characterId() {
		return this.characterID;
	}

	@Override
	public AuthenticatedCharacter newRefreshToken(final AccessToken accessToken) {
		this.oAuth2AccessToken = accessToken;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final FakeAuthenticatedCharacter that = (FakeAuthenticatedCharacter) o;

		return characterID == that.characterID;
	}

	@Override
	public int hashCode() {
		return (int) characterID;
	}

	@Override
	public String toString() {
		return "FakeAuthenticatedCharacter{" +
				"characterName='" + this.characterName() + '\'' +
				", characterID='" + this.characterId() + '\'' +
				'}';
	}
}
