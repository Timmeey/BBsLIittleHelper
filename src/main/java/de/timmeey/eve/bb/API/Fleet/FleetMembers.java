package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMembers {

	FleetMember byCharacter(final Character character, final Fleet fleet, final AccessToken accessToken);


}
