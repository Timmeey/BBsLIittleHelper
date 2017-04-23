package de.timmeey.eve.bb.spai;

import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 23.04.17.
 */
public class SpyMeasuredFleetsImpl implements Fleets {
	private final Fleets fleets;
	private final FleetMembers fLeetMembers;
	private final SpyScores spyScores;

	public SpyMeasuredFleetsImpl(final Fleets fleets, final FleetMembers fLeetMembers, final SpyScores spyScores) {
		this.fleets = fleets;
		this.fLeetMembers = fLeetMembers;
		this.spyScores = spyScores;
	}

	@Override
	public SpyMeasuredFleet byId(final long id, final AccessToken accessToken) throws Exception {
		return new SpyMeasuredFleet(fleets.byId(id, accessToken), fLeetMembers, spyScores);
	}
}
