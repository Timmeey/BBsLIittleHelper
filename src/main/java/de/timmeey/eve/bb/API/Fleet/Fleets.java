package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.OAuth2.AccessToken;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Fleets {
	Fleet byId(long id, AccessToken accessToken) throws Exception;
}
