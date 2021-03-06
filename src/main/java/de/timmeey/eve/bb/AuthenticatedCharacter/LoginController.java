package de.timmeey.eve.bb.AuthenticatedCharacter;

import com.google.common.collect.Lists;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import de.timmeey.eve.bb.OAuth2.AuthenticatedController;
import de.timmeey.eve.bb.OAuth2.EveOAuth2Api;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.DELETE;
import ro.pippo.controller.GET;
import ro.pippo.controller.Named;
import ro.pippo.controller.Path;
import ro.pippo.controller.extractor.Param;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
@Path("/login")
public class LoginController extends AuthenticatedController {
	private final EveOAuth2Api sso;
	private final AuthenticatedCharacters authenticatedCharacters;

	public LoginController(final EveOAuth2Api sso, final AuthenticatedCharacters authenticatedCharacters) {

		this.sso = sso;
		this.authenticatedCharacters = authenticatedCharacters;
	}

	@GET
	@Named("eveLogin")
	public void loginNeeded() {
		if (isAuthenticated()) {
			log.info("Was already logged in. Redirecting");
			getResponse().redirect("/");
		} else {
			log.debug("Was not authenticated, redirecting to EveSSO");
			getResponse().redirect(sso.initialLoginRedirectUri("500").toASCIIString());
		}
	}

	@GET("/eve")
	public void acceptAuthorizationToken(@Param("code") String code, @Param("state") String state) throws IOException {
		log.debug("Retrieving authorization_code from a OAUTH2 redirect to here");
		log.debug("FleetId: {}", state);
		if (code != null && !code.isEmpty()) {
			AccessToken accToken = sso.redeemAuthorizationCode(code);
			AuthenticatedCharacter authenticatedCharacter = sso.obtainCharacter(accToken, authenticatedCharacters);
			this.authenticateSession(authenticatedCharacter);
		}
		getResponse().redirect("/");

	}

	@DELETE
	public void logout() {
		if (isAuthenticated()) {
			getRouteContext().resetSession();
		}
		getResponse().redirect("/");


	}

	public Stream<String> unsecuredURIs() {
		return Lists.newArrayList("/login.*").stream();
	}
}
