package de.timmeey.eve.bb;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Created by timmeey on 15.04.17.
 */
public class FakeAuthenticatedCharacter implements AuthenticatedCharacter {
	private final String characterName;
	private final String characterID;
	private final EveSSOProvider eveSSOProvider;
	private OAuth2AccessToken oAuth2AccessToken;

	protected FakeAuthenticatedCharacter(final String characterName, final String characterID, OAuth2AccessToken
			authToken, final EveSSOProvider eveSSO) {
		this.characterName = characterName;

		this.characterID = characterID;
		this.newRefreshToken(authToken);
		this.eveSSOProvider = eveSSO;
	}

	@Override
	public String characterName() {
		return this.characterName;
	}

	@Override
	public String characterId() {
		return this.characterID;
	}

	@Override
	public String accessToken() {
		return null;
	}

	@Override
	public AuthenticatedCharacter newRefreshToken(final OAuth2AccessToken authToken) {
		this.oAuth2AccessToken = authToken;

		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final FakeAuthenticatedCharacter that = (FakeAuthenticatedCharacter) o;

		return characterID.equals(that.characterID);
	}

	@Override
	public int hashCode() {
		return characterID.hashCode();
	}

	@Override
	public String toString() {
		return "FakeAuthenticatedCharacter{" +
				"characterName='" + this.characterName() + '\'' +
				", characterID='" + this.characterId() + '\'' +
				'}';
	}

	public void doRefreshToken() {
		String oldAccToken = this.oAuth2AccessToken.getValue();
		this.newRefreshToken(this.eveSSOProvider.newAccessToken(this, this.oAuth2AccessToken));
		String newAccToken = this.oAuth2AccessToken.getValue();
		System.out.printf("Old: %s, new: %s\n", oldAccToken, newAccToken);
	}
}
