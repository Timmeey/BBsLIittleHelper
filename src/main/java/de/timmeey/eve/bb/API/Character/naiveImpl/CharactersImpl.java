package de.timmeey.eve.bb.API.Character.naiveImpl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.API.Character.Portraits;
import de.timmeey.eve.bb.API.Corporation.Corporations;
import io.swagger.client.api.CharacterApi;

/**
 * Created by timmeey on 17.04.17.
 */
public class CharactersImpl implements Characters {

	private final CharacterApi characterApi;
	private final Corporations corporations;
	private final Portraits portraits;

	public CharactersImpl(final CharacterApi characterApi, final Corporations corporations, final Portraits
			portraits) {

		this.characterApi = characterApi;
		this.corporations = corporations;
		this.portraits = portraits;
	}

	@Override
	public Character byId(final long id) {
		return new CharacterImpl(id, characterApi, corporations, portraits);
	}
}
