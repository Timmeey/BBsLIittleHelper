package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Fleets {
	Fleet byId(long id, AccessToken accessToken) throws Exception;

	class FakeFleets implements Fleets {
		private final FleetMembers fleetMembers;
		private final Characters characters;
		private final AccessToken accessToken;

		public FakeFleets(final FleetMembers fleetMembers, final Characters characters, final AccessToken
				accessToken) {

			this.fleetMembers = fleetMembers;
			this.characters = characters;
			this.accessToken = accessToken;
		}

		@Override
		public Fleet byId(final long id, final AccessToken accessToken) throws Exception {
			return new Fleet.FakeFleet((int) id, fleetMembers, characters, accessToken);
		}
	}
}
