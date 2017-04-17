package de.timmeey.eve.bb.API.Fleet.impl;

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
	private final AccessToken accessToken;
	private final FleetMembers fleetMembers;

	public FleetsImpl(@NonNull final FleetsApi fleetsApi, @NonNull final AccessToken accessToken, final FleetMembers
			fleetMembers) {

		this.fleetsApi = fleetsApi;
		this.accessToken = accessToken;
		this.fleetMembers = fleetMembers;
	}

	public Fleet byId(@NonNull long id) throws Exception {
		return new FleetImpl(id, fleetsApi, accessToken, fleetMembers);
	}


}
