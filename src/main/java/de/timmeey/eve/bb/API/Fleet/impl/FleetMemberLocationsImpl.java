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
	private final AccessToken accessToken;

	public FleetMemberLocationsImpl(final Stations stations, final Systems systems, final FleetsApi fleetsApi, final
	AccessToken accessToken) {
		this.stations = stations;
		this.systems = systems;
		this.fleetsApi = fleetsApi;
		this.accessToken = accessToken;
	}

	@Override
	public FleetMemberLocation byFleetMemberId(final Character character, final Fleet fleet) {
		return new FleetMemberLocationImpl(systems, stations, fleet, character, fleetsApi, accessToken);
	}

}
