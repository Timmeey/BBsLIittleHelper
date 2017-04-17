package de.timmeey.eve.bb.API.Fleet.impl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocation;
import de.timmeey.eve.bb.API.Universe.Station;
import de.timmeey.eve.bb.API.Universe.Stations;
import de.timmeey.eve.bb.API.Universe.System.System;
import de.timmeey.eve.bb.API.Universe.System.Systems;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;
import io.swagger.client.model.GetFleetsFleetIdMembers200Ok;
import lombok.val;

import java.util.Optional;


/**
 * Created by timmeey on 17.04.17.
 */
public class FleetMemberLocationImpl implements FleetMemberLocation {

	private final Systems systems;
	private final Stations stations;
	private final Fleet fleet;
	private final Character character;
	private final FleetsApi fleetsApi;
	private final AccessToken accessToken;

	public FleetMemberLocationImpl(final Systems systems, final Stations stations, final Fleet fleet, final Character
			character, final FleetsApi fleetsApi, final AccessToken accessToken) {
		this.systems = systems;
		this.stations = stations;
		this.fleet = fleet;
		this.character = character;
		this.fleetsApi = fleetsApi;
		this.accessToken = accessToken;
	}

	@Override
	public System system() throws Exception {
		return systems.byId(getFleetMemberResponse().getSolarSystemId());
	}

	@Override
	public Optional<Station> station() throws Exception {
		val stationId = getFleetMemberResponse().getStationId();
		if (stationId == null || stationId == 0) {
			return Optional.empty();
		} else {
			return Optional.of(stations.byId(stationId));
		}
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}


	private GetFleetsFleetIdMembers200Ok getFleetMemberResponse() throws Exception {
		return fleetsApi.getFleetsFleetIdMembers(fleet.id(), null, null, accessToken.getAccessTokenString(), null,
				null).stream()
				.filter(memberResponse -> memberResponse.getCharacterId() == this.character.id()).findAny().get();
	}
}
