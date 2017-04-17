package de.timmeey.eve.bb.API.Universe.System;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Position3D {

	float distanceTo(float x, float y, float z);

	float distanceTo(Position3D position);
}
