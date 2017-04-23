package de.timmeey.eve.bb.spai;

import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Created by timmeey on 23.04.17.
 */
public interface SpyMeasure {

	String measureExplanation();

	Optional<String> resultExplanation() throws Exception;

	/**
	 * Likelyhood of this Character beign a spy according to this measure.
	 * Range from 0 to 1;
	 *
	 * @return
	 */
	OptionalDouble score();
}
