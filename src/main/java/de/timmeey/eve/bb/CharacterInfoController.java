package de.timmeey.eve.bb;

import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.OAuth2.AuthenticatedController;
import io.swagger.client.ApiException;
import io.swagger.client.api.CharacterApi;
import io.swagger.client.model.GetCharactersCharacterIdOk;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.GET;
import ro.pippo.controller.Path;
import ro.pippo.controller.Produces;
import ro.pippo.controller.extractor.Param;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
@Path("/")
public class CharacterInfoController extends AuthenticatedController {

	private final Characters characters;

	public CharacterInfoController(final Characters characters) {
		this.characters = characters;
	}

	@Produces(Produces.JSON)
	@GET
	public GetCharactersCharacterIdOk showCharacterBasics() {

		CharacterApi charApi = new CharacterApi();
		try {
			return charApi.getCharactersCharacterId((int) getCurrentAuthenticatedCharacter().get().characterId(),
					null, null, null);
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}
	}


	@GET("character/{characterId}")
	public String getCharacterDetails(@Param long characterId) {
		return characters.byId(characterId).toString();
	}


}
