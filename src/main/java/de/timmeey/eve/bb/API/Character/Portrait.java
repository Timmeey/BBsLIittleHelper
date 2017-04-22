package de.timmeey.eve.bb.API.Character;

import java.net.URI;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Portrait {

	URI px512() throws Exception;

	URI px256() throws Exception;

	URI px128() throws Exception;

	URI px64() throws Exception;

}
