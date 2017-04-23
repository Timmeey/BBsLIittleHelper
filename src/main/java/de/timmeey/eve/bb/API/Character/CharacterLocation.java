package de.timmeey.eve.bb.API.Character;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.timmeey.eve.bb.API.Universe.Station;
import de.timmeey.eve.bb.API.Universe.System.System;

import java.util.Optional;

/**
 * Created by timmeey on 17.04.17.
 */
public interface CharacterLocation {


	@JsonProperty("solarSystem")
	de.timmeey.eve.bb.API.Universe.System.System system() throws Exception;

	@JsonProperty("station")
	Optional<Station> station() throws Exception;

	@JsonProperty("isDocked")
	default boolean isDocked() throws Exception {
		return station().isPresent();
	}

	boolean isAuthenticated();

	class FakeCharacterLocation implements CharacterLocation {
		private final System system;
		private final Optional<Station> station;
		private final boolean istAuthenticated;

		public FakeCharacterLocation(final System system, final Optional<Station> station, final boolean
				istAuthenticated) {
			this.system = system;
			this.station = station;
			this.istAuthenticated = istAuthenticated;
		}

		@Override
		public System system() throws Exception {
			return system;
		}

		@Override
		public Optional<Station> station() throws Exception {
			return station;
		}

		@Override
		public boolean isAuthenticated() {
			return istAuthenticated;
		}
	}
}
