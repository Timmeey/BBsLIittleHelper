package de.timmeey.eve.bb.OAuth2;

import lombok.Value;

/**
 * Created by timmeey on 16.04.17.
 */
@Value
public class OAuthCharacterDTO {
	private long characterId;
	private String characterName;
	private String expiresOn;
	private String scopes;
	private String tokenType;
	private String CharacterOwnerHash;

}
