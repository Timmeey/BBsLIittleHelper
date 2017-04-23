package de.timmeey.eve.bb.API.Universe.System;

import de.timmeey.eve.bb.API.Alliance.Faction;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Systems {

	System byName(String name);

	System byId(int id);


	class FakeSystems implements Systems {

		@Override
		public System byName(final String name) {
			return new System.FakeSystem(name, null, new SystemSecurity.FakeSystemSecurity((float) 0.3, SystemSecurity
					.SecurityType.LOW), null, new Faction.FakeFaction());
		}

		@Override
		public System byId(final int id) {
			return byName(String.valueOf(id));
		}
	}
}
