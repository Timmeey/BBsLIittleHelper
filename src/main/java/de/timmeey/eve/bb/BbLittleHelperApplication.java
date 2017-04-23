package de.timmeey.eve.bb;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import de.timmeey.eve.bb.API.Character.Characters;
import de.timmeey.eve.bb.API.Character.Portraits;
import de.timmeey.eve.bb.API.Character.naiveImpl.CharactersImpl;
import de.timmeey.eve.bb.API.Character.naiveImpl.PortraitsImpl;
import de.timmeey.eve.bb.API.Corporation.Corporations;
import de.timmeey.eve.bb.API.Corporation.naiveImpl.CorporationsImpl;
import de.timmeey.eve.bb.API.Fleet.FleetMemberLocations;
import de.timmeey.eve.bb.API.Fleet.FleetMembers;
import de.timmeey.eve.bb.API.Fleet.Fleets;
import de.timmeey.eve.bb.API.Fleet.naiveImpl.FleetMemberLocationsImpl;
import de.timmeey.eve.bb.API.Fleet.naiveImpl.FleetMembersImpl;
import de.timmeey.eve.bb.API.Fleet.naiveImpl.FleetsImpl;
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
import de.timmeey.eve.bb.fleet.FleetController;
import de.timmeey.eve.bb.spai.SpyMeasuredFleetsImpl;
import de.timmeey.eve.bb.spai.SpyScores;
import io.swagger.client.api.CharacterApi;
import io.swagger.client.api.FleetsApi;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.yaml.snakeyaml.Yaml;
import ro.pippo.controller.ControllerApplication;
import ro.pippo.core.Pippo;
import ro.pippo.core.RequestResponseFactory;
import ro.pippo.jackson.JacksonJsonEngine;
import ro.pippo.session.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.timmeey.eve.bb.OAuth2.AuthenticatedController.AUTHENTICATED_SESSION_KEY;

@Slf4j
public class BbLittleHelperApplication extends ControllerApplication {
	private final AuthenticatedCharacters authenticatedCharacters;
	private final EveOAuth2Api eveOAuth2Api;
	private final boolean offline = true;

	public BbLittleHelperApplication(final AuthenticatedCharacters authenticatedCharacters, final EveOAuth2Api
			eveOAuth2Api) {
		this.authenticatedCharacters = authenticatedCharacters;
		this.eveOAuth2Api = eveOAuth2Api;
	}

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		System.setProperty("pippo.mode", "dev");
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
	protected RequestResponseFactory createRequestResponseFactory() {
		SessionDataStorage sessionDataStorage = new MemorySessionDataStorage();
		SessionStrategy strategy = new CookieSessionStrategy() {
			@Override
			public String getRequestedSessionId(final HttpServletRequest httpServletRequest) {
				String headerSessId = this.getSessionIdFromHeader(httpServletRequest);
				if (headerSessId != null && !headerSessId.isEmpty()) {
					return headerSessId;
				}
				return super.getRequestedSessionId(httpServletRequest);
			}

			private String getSessionIdFromHeader(HttpServletRequest request) {
				return request.getHeader("Authorization");
			}


		};
		ro.pippo.session.SessionManager sessionManager = new ro.pippo.session.SessionManager(sessionDataStorage,
				strategy);

		return new SessionRequestResponseFactory(this, sessionManager);
	}

	@Override
	protected void onInit() {
		JacksonJsonEngine engine = new JacksonJsonEngine() {
			@Override
			protected ObjectMapper getObjectMapper() {
				return super.getObjectMapper().registerModule(new ParameterNamesModule())
						.registerModule(new Jdk8Module())
						.registerModule(new JavaTimeModule());
			}
		};

		engine.init(this);
		this.getContentTypeEngines().setContentTypeEngine(engine);
		System.out.println(this.getContentTypeEngine("application/json"));
		LoginController loginController = new LoginController(this.eveOAuth2Api, this.authenticatedCharacters);
		List<String> unsecured = loginController.unsecuredURIs().collect(Collectors.toList());
		unsecured.add("/public.*");

		ALL("/.*", routeContext -> {
			log.info("Request for {} '{}' with: {}", routeContext.getRequestMethod(), routeContext.getRequestUri(),
					routeContext.getRequest());
			routeContext.next();
		});
		ALL("/.*", routeContext -> {
			if (unsecured.stream().anyMatch(unsecuredUri -> Pattern.matches(unsecuredUri, routeContext
					.getRequestUri()))) {
				routeContext.next();
			} else {
				if (routeContext.getSession(AUTHENTICATED_SESSION_KEY) != null) {
					routeContext.next();
				} else {
					routeContext.redirect("eveLogin", new HashMap<>());
				}
			}
		});


		this.addControllers(loginController);

		if (offline) {

			Systems systems = new Systems.FakeSystems();
			Corporations corporations = new CorporationsImpl();
			Portraits portraits = new Portraits.FakePortraits();
			Stations stations = new Stations.FakeStations();
			FleetMemberLocations fleetMemberLocations = new FleetMemberLocations.FakeFleetMemberLocations();
			Types types = new Types.FakeTypes();
			FleetMembers fleetMembers = new FleetMembers.FakeFleetMembers(fleetMemberLocations, types);
			Characters characters = new Characters.FakeCharacters(portraits);
			Fleets fleets = new Fleets.FakeFleets(fleetMembers, characters, null);
			this.addControllers(new FleetController(fleets));
			this.addControllers(new CharacterInfoController(characters));
			this.addPublicResourceRoute();
		} else {
			FleetsApi fleetsApi = new FleetsApi();
			Systems systems = new SystemsImpl();
			CharacterApi characterApi = new CharacterApi();
			Corporations corporations = new CorporationsImpl();
			Portraits portraits = new PortraitsImpl(characterApi);
			Stations stations = new StationsImpl();
			FleetMemberLocations fleetMemberLocations = new FleetMemberLocationsImpl(stations, systems, fleetsApi);
			Types types = new TypesImpl();
			FleetMembers fleetMembers = new FleetMembersImpl(fleetsApi, types, systems, fleetMemberLocations);
			Characters characters = new CharactersImpl(characterApi, corporations, portraits);
			Fleets fleets = new FleetsImpl(fleetsApi, fleetMembers, characters);
			val spyScores = new SpyScores();
			fleets = new SpyMeasuredFleetsImpl(fleets, fleetMembers, );

			this.addControllers(new FleetController(fleets));
			this.addControllers(new CharacterInfoController(characters));
			this.addPublicResourceRoute();
		}
	}
}

