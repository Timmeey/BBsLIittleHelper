package de.timmeey.eve.bb.API.Universe.System;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by timmeey on 17.04.17.
 */
public interface SystemSecurity {
	@JsonProperty("security")
	float security();

	@JsonProperty("securityType")
	SecurityType type();

	enum SecurityType {
		HIGH, LOW, NULL, WH
	}


	class FakeSystemSecurity implements SystemSecurity {
		private final float security;
		private final SecurityType type;

		public FakeSystemSecurity(final float security, final SecurityType type) {
			this.security = security;
			this.type = type;
		}

		@Override
		public float security() {
			return security;
		}

		@Override
		public SecurityType type() {
			return type;
		}
	}
}
