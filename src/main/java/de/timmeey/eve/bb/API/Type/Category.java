package de.timmeey.eve.bb.API.Type;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Category {

	String name();

	Collection<TypeGroup> groupes();


	class FakeCategory implements Category {

		@Override
		public String name() {
			return "Fake Category";
		}

		@Override
		public Collection<TypeGroup> groupes() {
			return new ArrayList<>();
		}
	}
}
