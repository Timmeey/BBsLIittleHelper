package de.timmeey.eve.bb.API.Character;

import de.timmeey.eve.bb.API.Corporation.Corporation;

/**
 * Created by timmeey on 17.04.17.
 */
public class CharacterImpl implements Character {
	private final long id;

	public CharacterImpl(final long id) {
		this.id = id;
	}

	@Override
	public long id() {
		return id;
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public Corporation corporation() {
		return null;
	}

	@Override
	public Portrait portrait() {
		return null;
	}
}
