package de.timmeey.eve.bb.API.Character;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Characters {

	Character byId(long id);


	class FakeCharacters implements Characters {

		private final Portraits portraits;

		public FakeCharacters(final Portraits portraits) {
			this.portraits = portraits;
		}

		@Override
		public Character byId(final long id) {
			System.out.println("Character for id: " + id);
			return new Character.FakeCharacter(id, "FakeCharacter" + id, portraits, (int) (Math.random() * 300));
		}
	}

}
