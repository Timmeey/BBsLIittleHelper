package de.timmeey.eve.bb.OAuth2;

import java.io.IOException;
import java.net.URI;

/**
 * Created by timmeey on 16.04.17.
 */
public interface OAuth2Api {

	/**
	 * Provides the full URI where to redirect the User to, for the initial authentication request
	 *
	 * @param state the state param that you want to use
	 * @return ready to go URI to redirect the user to
	 */
	URI initialLoginRedirectUri(String state);

	/**
	 * Provides the full URI where to redirect the User to, for the initial authentication request
	 *
	 * @return ready to go URI to redirect the user to
	 */
	default URI initialLoginRedirectUri() {
		return initialLoginRedirectUri("");
	}


	/**
	 * Uses the provided Authorization Code to redeem an actual access token
	 *
	 * @param authorizationCode the authorization code
	 * @return the AccessToken for the User that generated the AuthorizationCode
	 */
	AccessToken redeemAuthorizationCode(String authorizationCode) throws IOException;

	/**
	 * Uses the refresh token to get a new accessToken
	 *
	 * @param refreshToken the refreshToken
	 * @return a new accessToken
	 */
	AccessToken newAccessToken(String refreshToken) throws IOException;


}
