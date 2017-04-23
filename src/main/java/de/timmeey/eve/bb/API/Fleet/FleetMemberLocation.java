package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Character.CharacterLocation;
import de.timmeey.eve.bb.API.Universe.Station;
import de.timmeey.eve.bb.API.Universe.System.System;

import java.util.Optional;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMemberLocation extends CharacterLocation {

	class FakeFleetMemberLocation implements FleetMemberLocation {
		private final CharacterLocation location;

		public FakeFleetMemberLocation(final System system, final Optional<Station> station, final boolean
				isAuthenticated) {
			location = new FakeCharacterLocation(system, station, isAuthenticated);
		}

		@Override
		public System system() throws Exception {
			return location.system();
		}

		@Override
		public Optional<Station> station() throws Exception {
			return location.station();
		}

		@Override
		public boolean isDocked() throws Exception {
			return location.isDocked();
		}

		@Override
		public boolean isAuthenticated() {
			return location.isAuthenticated();
		}
	}
}
