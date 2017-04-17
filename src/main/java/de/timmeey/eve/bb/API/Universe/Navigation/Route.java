package de.timmeey.eve.bb.API.Universe.Navigation;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Route {

	System start();

	System desto();

	float distance();

	Route routeTo();
}
