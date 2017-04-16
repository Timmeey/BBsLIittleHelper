package de.timmeey.eve.bb;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacters;
import de.timmeey.eve.bb.AuthenticatedCharacter.LoginController;
import de.timmeey.eve.bb.AuthenticatedCharacter.fake.FakeAuthenticatedCharacters;
import de.timmeey.eve.bb.OAuth2.EveOAuth2Api;
import de.timmeey.eve.bb.OAuth2.EveOAuth2ApiImplBuilder;
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
		Map<String, Object> config = (Map<String, Object>) yaml.load(classloader.getResourceAsStream("application.yml"));

		EveOAuth2Api eveOAuth2Api = new EveOAuth2ApiImplBuilder()
				.setAccessTokenURI(new URI(extractConfigs(config, "eve.client.accessTokenUri")))
				.setClientId(extractConfigs(config, "eve.client.clientId"))
				.setClientSecret(extractConfigs(config, "eve.client.clientSecret"))
				.setGson(new Gson())
				.setHttpClient(new OkHttpClient())
				.setScopes(extractConfigs(config, "eve.client.scope"))
				.setUserAuthorizationUri(new URI(extractConfigs(config, "eve.client.userAuthorizationUri")))
				.setUserInfoUri(new URI(extractConfigs(config, "eve.resource.userInfoUri")))
				.setRedirectToHost(extractConfigs(config, "eve.client.redirectToHost"))
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

		ALL("/.*", routeContext -> {
			log.info("Request for {} '{}' with: {}", routeContext.getRequestMethod(), routeContext.getRequestUri(),
					routeContext.getRequest());
			routeContext.next();
		});
	}
}

