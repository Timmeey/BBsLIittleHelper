package de.timmeey.eve.bb.OAuth2;


import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacter;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.Controller;

import java.util.Optional;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
public class AuthenticatedController extends Controller {
	private final static String AUTHENTICATED_SESSION_KEY = "authenticatedCharacter";

	protected Optional<AuthenticatedCharacter> getCurrentAuthenticatedCharacter() {
		return Optional.ofNullable(getRouteContext().getSession(AUTHENTICATED_SESSION_KEY));
	}

	protected boolean isAuthenticated() {
		return Optional.ofNullable(getRouteContext().getSession(AUTHENTICATED_SESSION_KEY)).isPresent();
	}

	protected void authenticateSession(AuthenticatedCharacter authenticatedCharacter) {
		log.debug("Setting {}:{} as authenticated into session", authenticatedCharacter.characterName(),
				authenticatedCharacter.characterId());
		this.getRouteContext().setSession(AUTHENTICATED_SESSION_KEY, authenticatedCharacter);
	}
}
