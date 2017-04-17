package de.timmeey.eve.bb.API.Character;

/**
 * Created by timmeey on 17.04.17.
 */
public class CharactersImpl implements Characters {
	@Override
	public Character byId(final long id) {
		return new CharacterImpl(id);
	}
}
