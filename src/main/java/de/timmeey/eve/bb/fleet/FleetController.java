package de.timmeey.eve.bb.fleet;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.OAuth2.AuthenticatedController;
import de.timmeey.eve.bb.fleet.dto.FleetRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.GET;
import ro.pippo.controller.POST;
import ro.pippo.controller.Path;
import ro.pippo.controller.extractor.Bean;

/**
 * Created by timmeey on 17.04.17.
 */
@Slf4j
@Path("/fleet")
public class FleetController extends AuthenticatedController {
	private final Fleets fleets;

	public FleetController(final Fleets fleets) {
		this.fleets = fleets;
	}

	@GET
	public void getFleet() throws Exception {
		Fleet fleet = this.getRouteContext().getSession("fleet");
		if (fleet == null) {

		}
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
	}

	@POST
	public void registerFleet(@Bean FleetRegistrationDTO fleetRegistration) throws Exception {
		log.debug(fleetRegistration.toString());
		Fleet fleet = fleets.byId(fleetRegistration.getFleetId(), getCurrentAuthenticatedCharacter().get().accessToken
				());

		this.getRouteContext().setSession("fleet", fleet);
	}
}
