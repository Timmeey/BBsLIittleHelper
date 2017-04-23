package de.timmeey.eve.bb.API.Fleet.naiveImpl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Character.Portrait;
import de.timmeey.eve.bb.API.Corporation.Corporation;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocation;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocations;
import de.timmeey.eve.bb.API.Type.Type;
import de.timmeey.eve.bb.API.Type.Types;
import de.timmeey.eve.bb.API.Universe.System.Systems;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;
import io.swagger.client.model.GetFleetsFleetIdMembers200Ok;

import java.time.Instant;

/**
 * Created by timmeey on 17.04.17.
 */
public class FleetMemberImpl implements FleetMember {

	private final FleetsApi fleetsApi;
	private final Character character;
	private final Types types;
	private final Systems systems;
	private final AccessToken accessToken;
	private final Fleet fleet;
	private final FleetMemberLocations fleetMemberLocations;

	public FleetMemberImpl(final FleetsApi fleetsApi, final Character character, final Types types, final Systems
			systems, final AccessToken accessToken,
						   final Fleet fleet, final FleetMemberLocations fleetMemberLocations) {
		this.fleetsApi = fleetsApi;
		this.character = character;
		this.types = types;
		this.systems = systems;
		this.accessToken = accessToken;
		this.fleet = fleet;
		this.fleetMemberLocations = fleetMemberLocations;
	}


	@Override
	public long id() {
		return this.character.id();
	}

	@Override
	public String name() throws Exception {
		return character.name();
	}

	@Override
	public Corporation corporation() throws Exception {
		return character.corporation();
	}

	@Override
	public Portrait portrait() {
		return character.portrait();
	}

	@Override
	public int ageInDays() throws Exception {
		return character.ageInDays();
	}

	@Override
	public Instant joinTime() throws Exception {
		return Instant.ofEpochMilli(getFleetMemberResponse()
				.getJoinTime().getMillis());
	}

	@Override
	public FleetMemberLocation location() throws Exception {
		return fleetMemberLocations.byFleetMemberId(character, fleet, accessToken);
	}

	@Override
	public Type ship() throws Exception {
		return types.byId(getFleetMemberResponse().getShipTypeId());
	}

	private GetFleetsFleetIdMembers200Ok getFleetMemberResponse() throws Exception {
		return fleetsApi.getFleetsFleetIdMembers(fleet.id(), null, null, accessToken.getAccessTokenString(), null,
				null).stream()
				.filter(memberResponse -> memberResponse.getCharacterId() == this.character.id()).findAny().get();
	}
}
