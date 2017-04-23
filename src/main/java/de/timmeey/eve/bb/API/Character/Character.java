package de.timmeey.eve.bb.API.Character;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.timmeey.eve.bb.API.Corporation.Corporation;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Character {

	@JsonProperty("characterId")
	long id();

	@JsonProperty("characterName")
	String name() throws Exception;

	Corporation corporation() throws Exception;

	Portrait portrait();

	int ageInDays() throws Exception;

	class FakeCharacter implements Character {

		private final long id;
		private final String name;
		private final Portraits portraits;
		private final int ageInDays;

		public FakeCharacter(final long id, final String name, final Portraits portraits, final int ageInDays) {
			this.id = id;
			this.name = name;
			this.portraits = portraits;
			this.ageInDays = ageInDays;
		}

		@Override
		public long id() {
			return id;
		}

		@Override
		public String name() throws Exception {
			return name;
		}

		@Override
		public Corporation corporation() throws Exception {
			throw new NotImplementedException();
		}

		@Override
		@JsonProperty("portrait")
		public Portrait portrait() {
			return portraits.byCharacter(this);
		}

		@Override
		@JsonProperty("ageInDays")
		public int ageInDays() throws Exception {
			return ageInDays;
		}
	}


}
