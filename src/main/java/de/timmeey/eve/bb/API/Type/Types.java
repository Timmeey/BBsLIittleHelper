package de.timmeey.eve.bb.API.Type;

import java.util.ArrayList;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Types {


	Type byId(long id);


	class FakeTypes implements Types {

		@Override
		public Type byId(final long id) {
			return new Type.FakeType("Muh type", String.valueOf(id), new TypeGroup.FakeTypeGroup(null, "TypeGroup", new
					ArrayList<>()));
		}
	}
}
