package de.timmeey.eve.bb.API.Universe;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Stations {

	Station byId(long id);


	class FakeStations implements Stations {

		@Override
		public Station byId(final long id) {
			return new Station.FakeStation("FakeStationName");
		}
	}
}
