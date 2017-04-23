package de.timmeey.eve.bb.fleet;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.OAuth2.AuthenticatedController;
import de.timmeey.eve.bb.fleet.dto.FleetRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.DELETE;
import ro.pippo.controller.GET;
import ro.pippo.controller.POST;
import ro.pippo.controller.Path;
import ro.pippo.controller.extractor.Bean;
import ro.pippo.controller.extractor.Param;

import java.util.Optional;

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

	private Optional<Fleet> currentFleet() {
		return Optional.ofNullable(this.getRouteContext().getSession("fleet"));
	}
	@GET
	public void getFleet() throws Exception {
		currentFleet().ifPresent(fleet -> {

		});

	}



	@POST
	public void registerFleet(@Bean FleetRegistrationDTO fleetRegistration) throws Exception {
		log.debug(fleetRegistration.toString());
		Fleet fleet = fleets.byId(fleetRegistration.getFleetId(), getCurrentAuthenticatedCharacter().get().accessToken
				());

		this.getRouteContext().setSession("fleet", fleet);
	}

	@DELETE("/member/{characterId}")
	public void kickMember(@Param("characterId") int charachterId) {

	}
}
