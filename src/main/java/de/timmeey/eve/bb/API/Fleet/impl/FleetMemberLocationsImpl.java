package de.timmeey.eve.bb.API.Fleet.impl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocation;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocations;
import de.timmeey.eve.bb.API.Universe.Stations;
import de.timmeey.eve.bb.API.Universe.System.Systems;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;

/**
 * Created by timmeey on 17.04.17.
 */
public class FleetMemberLocationsImpl implements FleetMemberLocations {

	private final Stations stations;
	private final Systems systems;
	private final FleetsApi fleetsApi;

	public FleetMemberLocationsImpl(final Stations stations, final Systems systems, final FleetsApi fleetsApi) {
		this.stations = stations;
		this.systems = systems;
		this.fleetsApi = fleetsApi;
	}

	@Override
	public FleetMemberLocation byFleetMemberId(final Character character, final Fleet fleet, AccessToken accessToken) {
		return new FleetMemberLocationImpl(systems, stations, fleet, character, fleetsApi, accessToken);
	}

}
