package de.timmeey.eve.bb.AuthenticatedCharacter;

import de.timmeey.eve.bb.OAuth2.AccessToken;
import de.timmeey.eve.bb.OAuth2.EveOAuth2Api;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.Controller;
import ro.pippo.controller.GET;
import ro.pippo.controller.Path;
import ro.pippo.controller.extractor.Param;

import java.io.IOException;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
@Path("/login")
public class LoginController extends Controller {
	private final EveOAuth2Api sso;
	private final AuthenticatedCharacters authenticatedCharacters;

	public LoginController(final EveOAuth2Api sso, final AuthenticatedCharacters authenticatedCharacters) {

		this.sso = sso;
		this.authenticatedCharacters = authenticatedCharacters;
	}

	@GET
	public void loginNeeded() {
		if (getRouteContext().getSession("characterId") != null) {
			log.info("Was already logged in. Redirecting");
			getResponse().redirect("/");
		} else {
			getResponse().redirect(sso.initialLoginRedirectUri().toASCIIString());
		}

	}

	@GET("/eve")
	public void acceptAuthorizationToken(@Param("code") String code, @Param("state") String state) throws IOException {
		System.out.println(code);
		log.debug(code, state);
		if (code != null && !code.isEmpty()) {
			AccessToken accToken = sso.redeemAuthorizationCode(code);
			AuthenticatedCharacter authenticatedCharacter = sso.obtainCharacter(accToken, authenticatedCharacters);
			getRouteContext().setSession("characterId", authenticatedCharacter.characterId());
			System.out.println(authenticatedCharacter);
		}
		getResponse().redirect("/");

	}
}
