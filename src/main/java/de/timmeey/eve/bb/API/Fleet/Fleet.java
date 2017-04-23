package de.timmeey.eve.bb.API.Fleet;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.OAuth2.AccessToken;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Fleet {


	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "characterId")
	@JsonProperty("members")
	List<FleetMember> members() throws Exception;

	@JsonProperty("fleetId")
	long id();

	class FakeFleet implements Fleet {
		private final int id;
		private final FleetMembers fleetMembers;
		private final Characters characters;
		private final AccessToken accessToken;
		private final List<FleetMember> members;

		public FakeFleet(final int id, final FleetMembers fleetMembers, final Characters characters, final AccessToken
				accessToken) {
			this.id = id;
			this.fleetMembers = fleetMembers;
			this.characters = characters;
			this.accessToken = accessToken;
			this.members = IntStream.range(0, (int) (Math.random() * 30)).mapToObj(charId -> fleetMembers.byCharacter
					(characters
					.byId(charId), this, accessToken)).collect(Collectors.toList());
		}

		@Override
		public List<FleetMember> members() throws Exception {
			return members;
		}

		@Override
		public long id() {
			return id;
		}
	}
}
