package de.timmeey.eve.bb.API.Universe;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.timmeey.eve.bb.API.Universe.System.System;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Station {

	@JsonProperty("name")
	String name();

	System system();


	class FakeStation implements Station {
		private final String name;

		public FakeStation(final String name) {
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public System system() {
			throw new NotImplementedException();
		}
	}
}
