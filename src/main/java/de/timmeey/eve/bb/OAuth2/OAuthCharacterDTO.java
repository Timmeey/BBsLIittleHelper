package de.timmeey.eve.bb.OAuth2;

import lombok.Value;

/**
 * Created by timmeey on 16.04.17.
 */
@Value
public class OAuthCharacterDTO {
	private long CharacterID;
	private String CharacterName;
	private String ExpiresOn;
	private String Scopes;
	private String TokenType;
	private String CharacterOwnerHash;

}
