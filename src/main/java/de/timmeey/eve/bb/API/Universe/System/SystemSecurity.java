package de.timmeey.eve.bb.API.Universe.System;

/**
 * Created by timmeey on 17.04.17.
 */
public interface SystemSecurity {
	float security();

	SecurityType type();

	enum SecurityType {
		HIGH, LOW, NULL, WH
	}
}
