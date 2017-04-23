package de.timmeey.eve.bb.spai.measures.age;

/**
 * Created by timmeey on 23.04.17.
 */
public interface ShipAgeAssumption {

	String explanation() throws Exception;

	int assumedMinAge() throws Exception;
}
