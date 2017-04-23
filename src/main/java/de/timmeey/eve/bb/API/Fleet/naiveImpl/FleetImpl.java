package de.timmeey.eve.bb.API.Fleet.naiveImpl;

import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
public class FleetImpl implements Fleet {

	private final long id;
	private final FleetsApi fleetsApi;
	private final AccessToken accessToken;
	private final FleetMembers fleetMembers;
	private final Characters characters;

	protected FleetImpl(@NonNull final long id, @NonNull final FleetsApi fleetsApi, @NonNull final AccessToken
			accessToken, final FleetMembers fleetMembers, final Characters characters) {
		this.id = id;
		this.fleetsApi = fleetsApi;
		this.accessToken = accessToken;
		this.fleetMembers = fleetMembers;
		this.characters = characters;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final FleetImpl fleet = (FleetImpl) o;

		return id == fleet.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public List<FleetMember> members() throws Exception {
		String accessString = accessToken.getAccessTokenString();
		log.debug(accessString);
		val response = fleetsApi.getFleetsFleetIdMembers(id, null, null, accessString, null, null);
		return response.stream().map(fleetMemberResponse -> fleetMembers.byCharacter(characters.byId
				(fleetMemberResponse
						.getCharacterId()), this, accessToken)).collect(Collectors.toList());
	}

	@Override
	public long id() {
		return id;
	}
}
