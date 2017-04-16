package de.timmeey.eve.bb.OAuth2;

import lombok.Value;

/**
 * Created by timmeey on 16.04.17.
 */
@Value
public class AccessTokenDTO {

	private String access_token;
	private String token_type;
	private long expires_in;
	private String refresh_token;

	@Override
	public String toString() {
		return "AccessTokenDTO{" +
				"access_token='" + access_token + '\'' +
				", token_type='" + token_type + '\'' +
				", expires_in=" + expires_in +
				", refresh_token='" + "<Omitted>" + '\'' +
				'}';
	}
}
