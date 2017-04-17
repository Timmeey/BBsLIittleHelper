package de.timmeey.eve.bb;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.OAuth2.AuthenticatedController;
import ro.pippo.controller.GET;
import ro.pippo.controller.Path;
import ro.pippo.controller.extractor.Param;

/**
 * Created by timmeey on 17.04.17.
 */
@Path("/fleet")
public class FleetController extends AuthenticatedController {
	private final Fleets fleets;

	public FleetController(final Fleets fleets) {
		this.fleets = fleets;
	}

	@GET("/{fleetId}")
	public String getFleet(@Param("fleetId") long fleetId) throws Exception {
		Fleet fleet = fleets.byId(fleetId, getCurrentAuthenticatedCharacter().get().accessToken());
		StringBuilder resultBuilder = new StringBuilder().append(fleet.id()).append("\n");
		fleet.members().forEach((fleetMember) -> {
			try {
				System.out.printf("Printing fleetmember %s:%s", fleetMember.name(), fleetMember.id());
				resultBuilder.append(fleetMember.name())
						.append(fleetMember.location().system().name())
						.append(fleetMember.ship().name())
						.append("\n");
			} catch (Exception e) {

			}
		});
		return resultBuilder.toString();


	}
}
