package de.timmeey.eve.bb.API.Universe.System;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Position3D {

	float distanceTo(float x, float y, float z);

	float distanceTo(Position3D position);


	class FakePosition implements Position3D {

		@Override
		public float distanceTo(final float x, final float y, final float z) {
			throw new NotImplementedException();
		}

		@Override
		public float distanceTo(final Position3D position) {
			throw new NotImplementedException();
		}
	}
}
