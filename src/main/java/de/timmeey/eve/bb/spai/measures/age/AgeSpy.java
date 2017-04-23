package de.timmeey.eve.bb.spai.measures.age;

import de.timmeey.eve.bb.API.Fleet.Fleet;
import de.timmeey.eve.bb.API.Fleet.FleetMember;
import de.timmeey.eve.bb.API.Type.Types;
import de.timmeey.eve.bb.spai.SpyMeasure;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Created by timmeey on 23.04.17.
 */
@Slf4j
public class AgeSpy implements SpyMeasure {

	private final FleetMember character;
	private final Fleet fleet;
	private final Types ships;
	private final ShipsAgeAssumptions shipsAgeAssumptions;

	public AgeSpy(final FleetMember character, final Fleet fleet, final Types ships, final ShipsAgeAssumptions
			shipsAgeAssumptions) {
		this.character = character;
		this.fleet = fleet;
		this.ships = ships;
		this.shipsAgeAssumptions = shipsAgeAssumptions;
	}

	@Override
	public String measureExplanation() {
		return String.format("Measures whether this Character old enough to realistically fly the ship she/he is " +
				"sitting in");
	}

	@Override
	public Optional<String> resultExplanation() throws Exception {
		final Optional<ShipAgeAssumptionImpl> assumption = shipsAgeAssumptions.assumption(character.ship());
		if (assumption.isPresent()) {
			return Optional.of(assumption.get().explanation());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public OptionalDouble score() {
		try {
			final Optional<ShipAgeAssumptionImpl> assumption = shipsAgeAssumptions.assumption(character.ship());
			if (assumption.isPresent()) {
				return OptionalDouble.of(calculateScore(character.ageInDays(), assumption.get().assumedMinAge()));
			} else {
				return OptionalDouble.empty();
			}
		} catch (Exception e) {
			log.warn("Getting the score threw exception, returning empty otpional", e);
			return OptionalDouble.empty();
		}
	}

	private double calculateScore(int age, int minAge) {
		return Math.max(0, minAge - age * (1 / minAge));
	}
}
