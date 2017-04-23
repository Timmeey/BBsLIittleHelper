package de.timmeey.eve.bb.API.Type;

import io.swagger.client.ApiException;
import io.swagger.client.api.UniverseApi;

import java.util.stream.Stream;

/**
 * Created by timmeey on 23.04.17.
 */
public class TypeGroupImpl implements TypeGroup {

	private final UniverseApi typeApi;
	private final Types types;
	private final int id;

	public TypeGroupImpl(final int id, final UniverseApi typeApi, final Types types) {
		this.typeApi = typeApi;
		this.id = id;
		this.types = types;
	}

	@Override
	public Category category() {
		return new Category.FakeCategory();
	}

	@Override
	public String name() throws ApiException {
		return typeApi.getUniverseGroupsGroupId(id, null, null, null, null).getName();
	}

	@Override
	public Stream<Type> types() throws ApiException {
		return typeApi.getUniverseGroupsGroupId(id, null, null, null, null).getTypes().stream().map(typeId -> types
				.byId(typeId));
	}
}
