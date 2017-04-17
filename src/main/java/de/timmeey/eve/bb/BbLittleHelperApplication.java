package de.timmeey.eve.bb;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.API.Character.CharactersImpl;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocations;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.API.Fleet.impl.FleetMemberLocationsImpl;
import de.timmeey.eve.bb.API.Fleet.impl.FleetMembersImpl;
import de.timmeey.eve.bb.API.Fleet.impl.FleetsImpl;
import de.timmeey.eve.bb.API.Type.Types;
import de.timmeey.eve.bb.API.Type.TypesImpl;
import de.timmeey.eve.bb.API.Universe.Stations;
import de.timmeey.eve.bb.API.Universe.StationsImpl;
import de.timmeey.eve.bb.API.Universe.System.Systems;
import de.timmeey.eve.bb.API.Universe.System.SystemsImpl;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacters;
import de.timmeey.eve.bb.AuthenticatedCharacter.LoginController;
import de.timmeey.eve.bb.AuthenticatedCharacter.fake.FakeAuthenticatedCharacters;
import de.timmeey.eve.bb.OAuth2.EveOAuth2Api;
import de.timmeey.eve.bb.OAuth2.EveOAuth2ApiImplBuilder;
import io.swagger.client.api.FleetsApi;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import ro.pippo.controller.ControllerApplication;
import ro.pippo.core.Pippo;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class BbLittleHelperApplication extends ControllerApplication {
	private final AuthenticatedCharacters authenticatedCharacters;
	private final EveOAuth2Api eveOAuth2Api;

	public BbLittleHelperApplication(final AuthenticatedCharacters authenticatedCharacters, final EveOAuth2Api
			eveOAuth2Api) {
		this.authenticatedCharacters = authenticatedCharacters;
		this.eveOAuth2Api = eveOAuth2Api;
	}

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		Yaml yaml = new Yaml();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Map<String, Object> generalConfig = (Map<String, Object>) yaml.load(classloader.getResourceAsStream
				("application.yml"));
		Map<String, Object> specificConfig = (Map<String, Object>) yaml.load(classloader.getResourceAsStream
				("eveApiKeys.yml"));


		EveOAuth2Api eveOAuth2Api = new EveOAuth2ApiImplBuilder()
				.setAccessTokenURI(new URI(extractConfigs(generalConfig, "eve.client.accessTokenUri")))
				.setClientId(extractConfigs(specificConfig, "eve.client.clientId"))
				.setClientSecret(extractConfigs(specificConfig, "eve.client.clientSecret"))
				.setGson(new Gson())
				.setHttpClient(new OkHttpClient())
				.setScopes(extractConfigs(generalConfig, "eve.client.scope"))
				.setUserAuthorizationUri(new URI(extractConfigs(generalConfig, "eve.client.userAuthorizationUri")))
				.setUserInfoUri(new URI(extractConfigs(generalConfig, "eve.resource.userInfoUri")))
				.setRedirectToHost(extractConfigs(specificConfig, "eve.client.redirectToHost"))
				.createEveOAuth2ApiImpl();


		AuthenticatedCharacters authenticatedCharacters = new FakeAuthenticatedCharacters(eveOAuth2Api);

		BbLittleHelperApplication app = new BbLittleHelperApplication(authenticatedCharacters, eveOAuth2Api);
		Pippo pippo = new Pippo(app);
		pippo.start(8080);
	}

	private static String extractConfigs(Map<String, Object> map, String key) {
		if (key.contains(".")) {
			String[] keys = key.split("[.]");
			int index = key.indexOf(".");
			key = key.substring(index + 1);
			Object obj = map.get(keys[0]);
			if (obj instanceof Map) {
				return extractConfigs((Map<String, Object>) obj, key);
			} else {
				throw new IllegalArgumentException("Key depth did not match map depths");
			}
		} else {
			//anchor
			Object result = map.get(key);
			if (result instanceof Map) {
				throw new IllegalArgumentException("Map depth is deeper than key depths");
			} else {
				return result.toString();
			}
		}

	}

	@Override
	protected void onInit() {
		this.addControllers(new LoginController(this.eveOAuth2Api, this.authenticatedCharacters));
		this.addControllers(new CharacterInfoController());
		FleetsApi fleetsApi = new FleetsApi();
		Systems systems = new SystemsImpl();
		Stations stations = new StationsImpl();
		FleetMemberLocations fleetMemberLocations = new FleetMemberLocationsImpl(stations, systems, fleetsApi);
		Types types = new TypesImpl();
		FleetMembers fleetMembers = new FleetMembersImpl(fleetsApi, types, systems, fleetMemberLocations);
		Characters characters = new CharactersImpl();
		Fleets fleets = new FleetsImpl(fleetsApi, fleetMembers, characters);
		this.addControllers(new FleetController(fleets));

		ALL("/.*", routeContext -> {
			log.info("Request for {} '{}' with: {}", routeContext.getRequestMethod(), routeContext.getRequestUri(),
					routeContext.getRequest());
			routeContext.next();
		});
	}
}

