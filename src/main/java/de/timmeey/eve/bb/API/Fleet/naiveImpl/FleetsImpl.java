package de.timmeey.eve.bb.API.Fleet.naiveImpl;

import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.OAuth2.AccessToken;
import io.swagger.client.api.FleetsApi;
import lombok.NonNull;

/**
 * Created by timmeey on 16.04.17.
 */
public class FleetsImpl implements Fleets {
	private final FleetsApi fleetsApi;
	private final FleetMembers fleetMembers;
	private final Characters characters;

	public FleetsImpl(@NonNull final FleetsApi fleetsApi, final FleetMembers
			fleetMembers, final Characters characters) {

		this.fleetsApi = fleetsApi;
		this.fleetMembers = fleetMembers;
		this.characters = characters;
	}

	@Override
	public Fleet byId(@NonNull long id, @NonNull AccessToken accessToken) throws Exception {
		return new FleetImpl(id, fleetsApi, accessToken, fleetMembers, characters);
	}


}
