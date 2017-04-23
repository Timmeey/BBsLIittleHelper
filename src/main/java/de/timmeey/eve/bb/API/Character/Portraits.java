package de.timmeey.eve.bb.API.Character;

/**
 * Created by timmeey on 22.04.17.
 */
public interface Portraits {

	Portrait byCharacter(Character character);

	class FakePortraits implements Portraits {

		@Override
		public Portrait byCharacter(final Character character) {
			return new Portrait.FakePortrait();
		}
	}
}
