package de.timmeey.eve.bb.API.Type;

import java.util.Collection;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Category {

	String name();

	Collection<TypeGroup> groupes();
}
