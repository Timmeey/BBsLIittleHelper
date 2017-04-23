package de.timmeey.eve.bb.spai;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;

import java.util.stream.Stream;

/**
 * Created by timmeey on 23.04.17.
 */
public class SpyMeasuredFleet implements Fleet {
	private final Fleet parent;
	private final FleetMembers fleetMembers;
	private final SpyScores spyScores;

	public SpyMeasuredFleet(final Fleet fleet, final FleetMembers fleetMembers, final SpyScores spyScores) {
		this.parent = fleet;
		this.fleetMembers = fleetMembers;
		this.spyScores = spyScores;
	}

	public Stream<SpyMeasuredFleetMember> spyMeasuredMembers() throws Exception {
		return parent.members().map(member -> new SpyMeasuredFleetMember(member, spyScores, this)).sorted((member1,
																										   member2)
				-> {
			if (member1.spyScore().overallScore().isPresent() && member2.spyScore().overallScore().isPresent()) {
				return Double.compare(member1.spyScore().overallScore().getAsDouble(), member2.spyScore().overallScore
						().getAsDouble());
			} else {
				return -1;
			}
		});
	}

	@Override
	public Stream<FleetMember> members() throws Exception {
		return parent.members();
	}

	@Override
	public long id() {
		return parent.id();
	}
}
