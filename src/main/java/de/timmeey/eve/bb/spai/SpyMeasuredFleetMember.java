package de.timmeey.eve.bb.spai;

import de.timmeey.eve.bb.API.Character.Portrait;
import de.timmeey.eve.bb.API.Corporation.Corporation;
import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocation;
import de.timmeey.eve.bb.API.Type.Type;

import java.time.Instant;

/**
 * Created by timmeey on 23.04.17.
 */
public class SpyMeasuredFleetMember implements FleetMember {

	private final FleetMember parent;
	private final SpyScores spyScores;
	private final Fleet fleet;

	public SpyMeasuredFleetMember(final FleetMember parent, final SpyScores spyScores, final Fleet fleet) {
		this.parent = parent;
		this.spyScores = spyScores;
		this.fleet = fleet;
	}


	public SpyScore spyScore() {
		return spyScores.spyScore(this, fleet);
	}

	@Override
	public long id() {
		return parent.id();
	}

	@Override
	public String name() throws Exception {
		return parent.name();
	}

	@Override
	public Corporation corporation() throws Exception {
		return parent.corporation();
	}

	@Override
	public Instant joinTime() throws Exception {
		return parent.joinTime();
	}

	@Override
	public Portrait portrait() {
		return parent.portrait();
	}

	@Override
	public int ageInDays() throws Exception {
		return parent.ageInDays();
	}

	@Override
	public FleetMemberLocation location() throws Exception {
		return parent.location();
	}

	@Override
	public Type ship() throws Exception {
		return parent.ship();
	}
}
