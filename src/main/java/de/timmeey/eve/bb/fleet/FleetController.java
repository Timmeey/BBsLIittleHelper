package de.timmeey.eve.bb.fleet;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.OAuth2.AuthenticatedController;
import de.timmeey.eve.bb.fleet.dto.FleetRegistrationDTO;
import de.timmeey.eve.bb.spai.SpyMeasuredFleet;
import lombok.extern.slf4j.Slf4j;
import ro.pippo.controller.*;
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

	@Produces(Produces.JSON)
	@GET
	public SpyMeasuredFleet getFleet() throws Exception {
		log.debug("GetFleet called");
		final Fleet fleet = fleets.byId(47918, getCurrentAuthenticatedCharacter().get().accessToken
				());
//		currentFleet().ifPresent(fleet1 -> {
//			fleet[0] =fleet1;
//		});
		return fleet;

	}



	@POST
	public void registerFleet(@Bean FleetRegistrationDTO fleetRegistration) throws Exception {
		log.debug(fleetRegistration.toString());
		Fleet fleet = fleets.byId(fleetRegistration.getFleetId(), getCurrentAuthenticatedCharacter().get().accessToken
				());

		this.getRouteContext().setSession("fleet", fleet);
		getRouteContext().getResponse().created().commit();
	}

	@DELETE("/member/{characterId}")
	public void kickMember(@Param("characterId") int charachterId) {

	}
}
