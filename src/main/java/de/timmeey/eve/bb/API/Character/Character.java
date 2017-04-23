package de.timmeey.eve.bb.API.Character;

import de.timmeey.eve.bb.API.Corporation.Corporation;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Character {

	long id();

	String name() throws Exception;

	Corporation corporation() throws Exception;

	Portrait portrait();

	int ageInDays() throws Exception;


}
