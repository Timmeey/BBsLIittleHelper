package de.timmeey.eve.bb.API.Character.naiveImpl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Character.Portrait;
import io.swagger.client.api.CharacterApi;
import io.swagger.client.model.GetCharactersCharacterIdPortraitOk;

import java.net.URI;

/**
 * Created by timmeey on 22.04.17.
 */
public class PortraitImpl implements Portrait {


	private final CharacterApi characterApi;
	private final Character character;

	public PortraitImpl(final Character character, final CharacterApi characterApi) {
		this.characterApi = characterApi;
		this.character = character;
	}

	@Override
	public URI px512() throws Exception {
		return new URI(getPortraitResponse().getPx512x512());
	}

	@Override
	public URI px256() throws Exception {
		return new URI(getPortraitResponse().getPx256x256());
	}

	@Override
	public URI px128() throws Exception {
		return new URI(getPortraitResponse().getPx128x128());
	}

	@Override
	public URI px64() throws Exception {
		return new URI(getPortraitResponse().getPx64x64());
	}

	private GetCharactersCharacterIdPortraitOk getPortraitResponse() throws Exception {
		if (character.id() > Integer.MAX_VALUE) {
			throw new Exception(String.format("The ID %s was too long to be casted into an integer, which is required" +
					" " +
					"by the api", character.id()));
		}
		return characterApi.getCharactersCharacterIdPortrait((int) character.id(), null, null, null);
	}
}
