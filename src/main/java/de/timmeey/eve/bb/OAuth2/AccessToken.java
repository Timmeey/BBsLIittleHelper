package de.timmeey.eve.bb.OAuth2;


import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Created by timmeey on 16.04.17.
 */
public interface AccessToken {

	/**
	 * Always returns an accessToken that is valid.
	 * Fetches a new one if needed
	 *
	 * @return a valid accessToken
	 */
	String getAccessTokenString() throws IOException;

	String getTokenType();

	Instant validUntil();

	default long validFor() {
		return Instant.now().until(validUntil(), ChronoUnit.SECONDS);
	}
}
