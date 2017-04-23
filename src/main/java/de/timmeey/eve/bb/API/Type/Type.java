package de.timmeey.eve.bb.API.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by timmeey on 17.04.17.
 */
public interface Type {

	String description();

	@JsonProperty("typeName")
	String name();

	@JsonProperty("typeGroup")
	TypeGroup typeGroup();


	class FakeType implements Type {
		private final String description;
		private final String name;
		private final TypeGroup typeGroup;

		public FakeType(final String description, final String name, final TypeGroup typeGroup) {
			this.description = description;
			this.name = name;
			this.typeGroup = typeGroup;
		}

		@Override
		public String description() {
			return description;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public TypeGroup typeGroup() {
			return typeGroup;
		}
	}
}
