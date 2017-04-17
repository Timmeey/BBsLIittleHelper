package de.timmeey.eve.bb.API.Character;

import de.timmeey.eve.bb.API.Corporation.Corporation;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Character {

	long id();

	String name();

	Corporation corporation();

	Portrait portrait();


}
