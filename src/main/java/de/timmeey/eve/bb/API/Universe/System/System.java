package de.timmeey.eve.bb.API.Universe.System;

import de.timmeey.eve.bb.API.Alliance.Faction;
import de.timmeey.eve.bb.API.Universe.Navigation.Route;


/**
 * Created by timmeey on 17.04.17.
 */
public interface System {

	String name();

	Route routeTo(System desto);

	SystemSecurity security();

	Position3D position();

	Faction sovereignty();
}
