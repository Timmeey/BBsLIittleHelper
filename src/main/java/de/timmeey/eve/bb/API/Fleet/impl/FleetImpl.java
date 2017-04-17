package de.timmeey.eve.bb.API.Fleet.impl;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;
import lombok.NonNull;
import lombok.val;

import java.util.stream.Stream;

/**
 * Created by timmeey on 16.04.17.
 */
public class FleetImpl implements Fleet {

	private final long id;
	private final FleetsApi fleetsApi;
	private final AccessToken accessToken;
	private final FleetMembers fleetMembers;

	protected FleetImpl(@NonNull final long id, @NonNull final FleetsApi fleetsApi, @NonNull final AccessToken
			accessToken, final FleetMembers fleetMembers) {
		this.id = id;
		this.fleetsApi = fleetsApi;
		this.accessToken = accessToken;
		this.fleetMembers = fleetMembers;
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
	public Stream<FleetMember> members() throws Exception {
		val response = fleetsApi.getFleetsFleetIdMembers(id, null, accessToken.getAccessTokenString(), null, null,
				null);
		return response.stream().map(fleetMemberResponse -> fleetMembers.byCharacterId(fleetMemberResponse
				.getCharacterId()));
	}

	@Override
	public long id() {
		return id;
	}
}
