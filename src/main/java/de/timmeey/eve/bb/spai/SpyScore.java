package de.timmeey.eve.bb.spai;

import java.util.OptionalDouble;
import java.util.stream.Stream;

/**
 * Created by timmeey on 23.04.17.
 */
public interface SpyScore {

	default OptionalDouble overallScore() {
		return measures().filter(spy -> spy.score().isPresent()).mapToDouble(spy -> spy.score().getAsDouble())
				.average();
	}

	Stream<SpyMeasure> measures();


}
