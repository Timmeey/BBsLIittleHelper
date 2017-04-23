package de.timmeey.eve.bb.API.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.client.ApiException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by timmeey on 17.04.17.
 */
public interface TypeGroup {

	Category category();

	@JsonProperty("name")
	String name() throws ApiException;

	Stream<Type> types() throws ApiException;

	class FakeTypeGroup implements TypeGroup {

		private final Category category;
		private final String name;
		private final List<Type> types;

		public FakeTypeGroup(final Category category, final String name, final List<Type> types) {
			this.category = category;
			this.name = name;
			this.types = types;
		}

		@Override
		public Category category() {
			throw new NotImplementedException();
		}

		@Override
		public String name() throws ApiException {
			return name;
		}

		@Override
		public Stream<Type> types() throws ApiException {
			return types.stream();
		}
	}

}
