package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Alliance.Faction;
import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Fleet.naiveImpl.FleetMemberLocationImpl;
import de.timmeey.eve.bb.API.Universe.Station;
import de.timmeey.eve.bb.API.Universe.System.Position3D;
import de.timmeey.eve.bb.API.Universe.System.System;
import de.timmeey.eve.bb.API.Universe.System.SystemSecurity;
import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMemberLocations {

	FleetMemberLocation byFleetMemberId(Character character, Fleet fleet, AccessToken accessToken);


	class FakeFleetMemberLocations implements FleetMemberLocations {

		@Override
		public FleetMemberLocation byFleetMemberId(final Character character, final Fleet fleet, final AccessToken
				accessToken) {
			return new FleetMemberLocationImpl.FakeFleetMemberLocation(
					new System.FakeSystem("System", null, new SystemSecurity.FakeSystemSecurity(0.5f,
							SystemSecurity.SecurityType.HIGH), new Position3D.FakePosition(), new Faction
							.FakeFaction()),

					java.util.Optional.of(new Station.FakeStation("station1")),
					false);
		}
	}
}
