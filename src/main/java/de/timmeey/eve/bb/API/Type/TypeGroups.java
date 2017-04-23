package de.timmeey.eve.bb.API.Type;

import java.util.Optional;

/**
 * Created by timmeey on 23.04.17.
 */
public interface TypeGroups {

	TypeGroup byId(long id);

	Optional<TypeGroup> byName(String name);
}
