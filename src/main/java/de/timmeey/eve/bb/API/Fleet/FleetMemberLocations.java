package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMemberLocations {

	FleetMemberLocation byFleetMemberId(Character character, Fleet fleet, AccessToken accessToken);
}
