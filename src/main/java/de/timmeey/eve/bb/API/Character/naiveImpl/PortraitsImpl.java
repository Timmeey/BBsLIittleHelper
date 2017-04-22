package de.timmeey.eve.bb.API.Character.naiveImpl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Character.Portrait;
import de.timmeey.eve.bb.API.Character.Portraits;
import io.swagger.client.api.CharacterApi;

/**
 * Created by timmeey on 22.04.17.
 */
public class PortraitsImpl implements Portraits {
	private final CharacterApi characterApi;

	public PortraitsImpl(final CharacterApi characterApi) {
		this.characterApi = characterApi;
	}

	@Override
	public Portrait byCharacter(final Character character) {
		return new PortraitImpl(character, characterApi);
	}
}
