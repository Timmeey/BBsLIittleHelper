package de.timmeey.eve.bb.API.Fleet.impl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocations;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.API.Type.Types;
import de.timmeey.eve.bb.API.Universe.System.Systems;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;

/**
 * Created by timmeey on 17.04.17.
 */
public class FleetMembersImpl implements FleetMembers {


	private final FleetsApi fleetsApi;
	private final Types types;
	private final Systems systems;
	private final FleetMemberLocations fleetMemberLocations;

	public FleetMembersImpl(final FleetsApi fleetsApi, final Types types, final Systems systems, final
	FleetMemberLocations fleetMemberLocations) {
		this.fleetsApi = fleetsApi;
		this.types = types;
		this.systems = systems;
		this.fleetMemberLocations = fleetMemberLocations;
	}

	@Override
	public FleetMember byCharacter(final Character character, final Fleet fleet, final AccessToken accessToken) {
		return new FleetMemberImpl(fleetsApi, character, types, systems, accessToken, fleet, fleetMemberLocations);
	}
}
