package de.timmeey.eve.bb.API.Character;

import java.net.URI;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Portrait {

	URI px512() throws Exception;

	URI px256() throws Exception;

	URI px128() throws Exception;

	URI px64() throws Exception;


	class FakePortrait implements Portrait {

		@Override
		public URI px512() throws Exception {
			return new URI("https://www.google.de");
		}

		@Override
		public URI px256() throws Exception {
			return new URI("https://www.google.de");
		}

		@Override
		public URI px128() throws Exception {
			return new URI("https://www.google.de");
		}

		@Override
		public URI px64() throws Exception {
			return new URI("https://www.google.de");
		}
	}
}
