package de.timmeey.eve.bb.API.Corporation;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Corporations {

	Corporation byName(String name);

	Corporation byId(int id);
}
