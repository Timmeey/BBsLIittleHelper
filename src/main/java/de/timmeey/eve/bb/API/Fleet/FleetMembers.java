package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Type.Types;
import de.timmeey.eve.bb.OAuth2.AccessToken;

import java.time.Instant;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMembers {

	FleetMember byCharacter(final Character character, final Fleet fleet, final AccessToken accessToken);


	class FakeFleetMembers implements FleetMembers {
		private final FleetMemberLocations locations;
		private final Types types;

		public FakeFleetMembers(final FleetMemberLocations locations, final Types types) {
			this.locations = locations;
			this.types = types;
		}

		@Override
		public FleetMember byCharacter(final Character character, final Fleet fleet, final AccessToken accessToken) {
			System.out.println("Character for id: " + character.id());
			return new FleetMember.FakeFleetMember(
					character,
					Instant.now(), locations.byFleetMemberId(character, fleet, null), types.byId(60)
			);
		}
	}


}
