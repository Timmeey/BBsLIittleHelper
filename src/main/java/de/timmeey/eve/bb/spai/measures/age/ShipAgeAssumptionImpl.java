package de.timmeey.eve.bb.spai.measures.age;

import de.timmeey.eve.bb.API.Type.TypeGroup;

/**
 * Created by timmeey on 23.04.17.
 */
public class ShipAgeAssumptionImpl implements ShipAgeAssumption {
	private final TypeGroup typeGroup;
	private final int ageInDays;

	public ShipAgeAssumptionImpl(final TypeGroup typeGroup, final int ageInDays) {
		this.typeGroup = typeGroup;
		this.ageInDays = ageInDays;
	}


	@Override
	public String explanation() throws Exception {
		return String.format("To be able to fly a %s realistically a character would have to be over %s days old",
				typeGroup.name(), assumedMinAge());
	}

	@Override
	public int assumedMinAge() throws Exception {
		return ageInDays;
	}
}
