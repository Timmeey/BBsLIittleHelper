package de.timmeey.eve.bb.API.Fleet;

import java.util.stream.Stream;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Fleet {

	Stream<FleetMember> members() throws Exception;

	long id();
}
