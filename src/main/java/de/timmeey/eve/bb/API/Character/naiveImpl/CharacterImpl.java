package de.timmeey.eve.bb.API.Character.naiveImpl;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Character.Portrait;
import de.timmeey.eve.bb.API.Character.Portraits;
import de.timmeey.eve.bb.API.Corporation.Corporation;
import de.timmeey.eve.bb.API.Corporation.Corporations;
import io.swagger.client.api.CharacterApi;
import io.swagger.client.model.GetCharactersCharacterIdOk;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Created by timmeey on 17.04.17.
 */
public class CharacterImpl implements Character {
	private final long id;
	private final CharacterApi charApi;
	private final Corporations corporations;
	private final Portraits portraits;

	public CharacterImpl(final long id, final CharacterApi characterApi, final Corporations corporations, final
	Portraits portraits) {
		this.id = id;
		this.charApi = characterApi;
		this.corporations = corporations;
		this.portraits = portraits;
	}

	@Override
	public long id() {
		return id;
	}

	@Override
	public String name() throws Exception {
		return getCharResponse().getName();
	}

	@Override
	public Corporation corporation() throws Exception {
		return corporations.byId(getCharResponse().getCorporationId());
	}

	@Override
	public Portrait portrait() {
		return portraits.byCharacter(this);
	}

	@Override
	public int ageInDays() throws Exception {
		return (int) Instant.ofEpochMilli(getCharResponse().getBirthday().getMillis()).until(Instant.now(), ChronoUnit
				.DAYS);
	}


	private GetCharactersCharacterIdOk getCharResponse() throws Exception {
		if (id > Integer.MAX_VALUE) {
			throw new Exception(String.format("The ID %s was too long to be casted into an integer, which is required" +
					" " +
					"by the api", id));
		}
		return charApi.getCharactersCharacterId((int) id, null, null, null);
	}
}
