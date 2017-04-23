package de.timmeey.eve.bb.spai.measures.age;

import de.timmeey.eve.bb.API.Type.Type;
import de.timmeey.eve.bb.API.Type.TypeGroup;
import de.timmeey.eve.bb.API.Type.TypeGroups;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by timmeey on 23.04.17.
 */
public class ShipsAgeAssumptionsImpl implements ShipsAgeAssumptions {
	private final static int FORCE_RECON_ID = 833;
	private final static int FORCE_RECON_AGE = 356;
	private static final int STEALTH_BOMBER_ID = 834;
	private final static int STEALTH_BOMBER_AGE = 200;
	private static final int COVERT_OPS_ID = 830;
	private static final int COVERT_OPS_AGE = 120;
	private final static int INTERDICTOR_ID = 541;
	private final static int INTERDICTOR_AGE = 150;
	private final TypeGroups typeGroups;
	private final Map<TypeGroup, ShipAgeAssumptionImpl> ageAssumptions;

	public ShipsAgeAssumptionsImpl(final TypeGroups typeGroups) {
		this.typeGroups = typeGroups;
		this.ageAssumptions = new HashMap<>(4);
		addAgeAssumption(FORCE_RECON_ID, FORCE_RECON_AGE);
		addAgeAssumption(STEALTH_BOMBER_ID, STEALTH_BOMBER_AGE);
		addAgeAssumption(COVERT_OPS_ID, COVERT_OPS_AGE);
		addAgeAssumption(INTERDICTOR_ID, INTERDICTOR_AGE);


	}

	private void addAgeAssumption(int groupId, int ageInDays) {
		TypeGroup group = typeGroups.byId(groupId);
		ageAssumptions.put(group, new ShipAgeAssumptionImpl(group, ageInDays));

	}

	@Override
	public Optional<ShipAgeAssumptionImpl> assumption(final Type ship) {
		return Optional.ofNullable(ageAssumptions.get(ship.typeGroup()));
	}
}
