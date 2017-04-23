package de.timmeey.eve.bb.spai.measures.age;

import de.timmeey.eve.bb.API.Type.Type;

import java.util.Optional;

/**
 * Created by timmeey on 23.04.17.
 */
public interface ShipsAgeAssumptions {

	Optional<ShipAgeAssumptionImpl> assumption(Type ship);


}
