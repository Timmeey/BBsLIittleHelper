package de.timmeey.eve.bb.API.Fleet;

import de.timmeey.eve.bb.API.Character.Character;
import de.timmeey.eve.bb.API.Type.Type;

import java.time.Instant;

/**
 * Created by timmeey on 17.04.17.
 */
public interface FleetMember extends Character {


	Instant joinTime() throws Exception;

	FleetMemberLocation location() throws Exception;

	Type ship() throws Exception;


}
