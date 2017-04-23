package de.timmeey.eve.bb.API.Type;

import io.swagger.client.ApiException;

import java.util.stream.Stream;

/**
 * Created by timmeey on 17.04.17.
 */
public interface TypeGroup {

	Category category();

	String name() throws ApiException;

	Stream<Type> types() throws ApiException;

}
