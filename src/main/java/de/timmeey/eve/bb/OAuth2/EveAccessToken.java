package de.timmeey.eve.bb.OAuth2;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
public class EveAccessToken implements AccessToken {

	private final String tokenType;
	private final String refreshToken;
	private final OAuth2Api oAuth2Api;
	private String accessTokenString;
	private Instant validUntil;

	public EveAccessToken(@NonNull final AccessTokenDTO dto, @NonNull final OAuth2Api oAuth2Api1) {
		this.oAuth2Api = oAuth2Api1;
		try {
			this.accessTokenString = Preconditions.checkNotNull(dto.getAccess_token());
		} catch (NullPointerException e) {
			log.error("AccessToken DTO was invalid", e);
			throw e;
		}
		try {
			this.tokenType = Preconditions.checkNotNull(dto.getToken_type());
		} catch (NullPointerException e) {
			log.error("AccessToken DTO was invalid", e);
			throw e;
		}

		try {
			this.refreshToken = Preconditions.checkNotNull(dto.getRefresh_token());
		} catch (NullPointerException e) {
			log.error("AccessToken DTO was invalid", e);
			throw e;
		}

		this.validUntil = Instant.now().plus(Preconditions.checkNotNull(dto.getExpires_in()), ChronoUnit.SECONDS);


	}


	@Override
	public String getAccessTokenString() throws IOException {
		if (this.validUntil.isBefore(Instant.now())) {
			log.debug("AccessToken invalid, requesting new");
			refreshAccessToken();
		}
		return this.accessTokenString;
	}

	@Override
	public String getTokenType() {
		return null;
	}

	@Override
	public Instant validUntil() {
		return null;
	}


	private void refreshAccessToken() throws IOException {
		AccessToken result = this.oAuth2Api.newAccessToken(this.refreshToken);
		this.validUntil = result.validUntil();
		this.accessTokenString = result.getAccessTokenString();
	}

	@Override
	public String toString() {
		return "EveAccessToken{" +
				"tokenType='" + tokenType + '\'' +
				", oAuth2Api=" + oAuth2Api +
				", accessTokenString='" + accessTokenString + '\'' +
				", validUntil=" + validUntil +
				'}';
	}
}
