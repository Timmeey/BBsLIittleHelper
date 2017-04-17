package de.timmeey.eve.bb.API.Character;

import de.timmeey.eve.bb.API.Universe.Station;

import java.util.Optional;

/**
 * Created by timmeey on 17.04.17.
 */
public interface CharacterLocation {

	de.timmeey.eve.bb.API.Universe.System.System system() throws Exception;

	Optional<Station> station() throws Exception;

	default boolean isDocked() throws Exception {
		return station().isPresent();
	}

	boolean isAuthenticated();
}
