package de.timmeey.eve.bb.API.Fleet;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Character.Portrait;
import de.timmeey.eve.bb.API.Corporation.Corporation;
import de.timmeey.eve.bb.API.Type.Type;

import java.time.Instant;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMember extends Character {

	@JsonProperty("joinTime")
	Instant joinTime() throws Exception;

	@JsonProperty("location")
	FleetMemberLocation location() throws Exception;

	@JsonProperty("ship")
	Type ship() throws Exception;

	class FakeFleetMember implements FleetMember {

		private final Character parent;
		private final Instant joinTime;
		private final FleetMemberLocation location;
		private final Type ship;

		public FakeFleetMember(final Character parent, Instant joinTime, FleetMemberLocation location, Type ship) {
			this.parent = parent;
			this.joinTime = joinTime;
			this.location = location;
			this.ship = ship;
		}


		@Override
		public Instant joinTime() throws Exception {
			return joinTime;
		}

		@Override
		public FleetMemberLocation location() throws Exception {
			return location;
		}

		@Override
		public Type ship() throws Exception {
			return ship;
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
		public Portrait portrait() {
			return parent.portrait();
		}

		@Override
		public int ageInDays() throws Exception {
			return parent.ageInDays();
		}
	}


}
