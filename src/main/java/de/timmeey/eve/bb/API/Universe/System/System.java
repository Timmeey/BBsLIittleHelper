package de.timmeey.eve.bb.API.Universe.System;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.timmeey.eve.bb.API.Alliance.Faction;
import de.timmeey.eve.bb.API.Universe.Navigation.Route;
import de.timmeey.eve.bb.API.Universe.Navigation.Routes;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Created by timmeey on 17.04.17.
 */
public interface System {

	@JsonProperty("name")
	String name();

	Route routeTo(System desto);

	@JsonProperty("security")
	SystemSecurity security();

	Position3D position();

	@JsonProperty("sovereignty")
	Faction sovereignty();

	class FakeSystem implements System {
		private final String name;
		private final Routes routes;
		private final SystemSecurity security;
		private final Position3D position;
		private final Faction faction;

		public FakeSystem(final String name, final Routes routes, final SystemSecurity security, final Position3D
				position, final Faction faction) {
			this.name = name;
			this.routes = routes;
			this.security = security;
			this.position = position;
			this.faction = faction;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public Route routeTo(final System desto) {
			throw new NotImplementedException();
		}

		@Override
		public SystemSecurity security() {
			return security;
		}

		@Override
		public Position3D position() {
			return position;
		}

		@Override
		public Faction sovereignty() {
			return null;
		}
	}
}
