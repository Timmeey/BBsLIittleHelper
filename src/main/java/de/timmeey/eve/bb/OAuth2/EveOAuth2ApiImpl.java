package de.timmeey.eve.bb.OAuth2;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacter;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacters;
import lombok.NonNull;
import lombok.val;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.Set;

/**
 * Created by timmeey on 16.04.17.
 */
public class EveOAuth2ApiImpl implements EveOAuth2Api {
	private static final String CALLBACK = "/login/eve";
	private final String clientId;
	private final String clientSecret;
	private final URI accessTokenURI;
	private final URI userAuthorizationUri;
	private final Set<String> scopes;
	private final URI userInfoUri;
	private final OkHttpClient httpClient;
	private final Gson gson;
	private final String redirectToHost;

	public EveOAuth2ApiImpl(@NonNull final String clientId, @NonNull final String clientSecret, @NonNull final URI
			accessTokenURI, @NonNull final URI
									userAuthorizationUri, @NonNull final Set<String> scopes, @NonNull final URI
									userInfoUri, @NonNull final String redirectToHost, final
							OkHttpClient httpClient, final Gson gson) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.accessTokenURI = accessTokenURI;
		this.userAuthorizationUri = userAuthorizationUri;
		this.scopes = scopes;
		this.userInfoUri = userInfoUri;
		this.httpClient = httpClient;
		this.gson = gson;
		this.redirectToHost = redirectToHost;
	}

	@Override
	public URI initialLoginRedirectUri(@NonNull final String state) {
		return
				UriComponentsBuilder.newInstance()
						.scheme(userAuthorizationUri.getScheme())
						.host(userAuthorizationUri.getHost())
						.path(userAuthorizationUri.getPath())
						.queryParam("response_type", "code")
						.queryParam("redirect_uri", redirectToHost + CALLBACK)
						.queryParam("client_id", clientId)
						.queryParam("scope", Joiner.on(" ").skipNulls().join(scopes))
						.queryParam("state", state)
						.build().encode().toUri();

	}

	@Override
	public EveAccessToken redeemAuthorizationCode(@NonNull final String authorizationCode) throws IOException {
		RequestBody requestBody = new FormEncodingBuilder()
				.add("grant_type", "authorization_code")
				.add("code", authorizationCode)
				.build();

		Response response = postRequestBodyAuthenticated(requestBody);
		return convertResponseToAccessToken(response);


	}


	@Override
	public EveAccessToken newAccessToken(@NonNull final String refreshToken) throws IOException {
		RequestBody requestBody = new FormEncodingBuilder()
				.add("grant_type", "refresh_token")
				.add("refresh_token", refreshToken)
				.build();

		Response response = postRequestBodyAuthenticated(requestBody);
		return convertResponseToAccessToken(response);
	}

	@Override
	public AuthenticatedCharacter obtainCharacter(final AccessToken accessToken, final AuthenticatedCharacters
			authenticatedCharacters) throws IOException {

		Request request = new Request.Builder()
				.url(this.userInfoUri.toString())
				.addHeader("Authorization", String.format("Bearer %s", accessToken))
				.get()
				.build();
		Response response = httpClient.newCall(request).execute();
		OAuthCharacterDTO authCharDTO = gson.fromJson(response.body().charStream(), OAuthCharacterDTO.class);
		val authenticatedCharacter = authenticatedCharacters.add(authCharDTO.getCharacterId(), authCharDTO
				.getCharacterName(), accessToken);

		return authenticatedCharacter;
	}

	private String base64EncodedCredentials() {
		return Base64.getEncoder().encodeToString(String.format("%s:%s", this.clientId, this.clientSecret).getBytes());
	}

	private EveAccessToken convertResponseToAccessToken(Response response) throws IOException {
		AccessTokenDTO dto = gson.fromJson(response.body().string(), AccessTokenDTO.class);
		EveAccessToken accessToken = new EveAccessToken(dto, this);
		return accessToken;
	}

	private Response postRequestBodyAuthenticated(RequestBody body) throws IOException {
		Request request = new Request.Builder()
				.url(this.accessTokenURI.toString())
				.addHeader("Authorization", String.format("Basic %s", this.base64EncodedCredentials()))
				.post(body)
				.build();
		return httpClient.newCall(request).execute();
	}
}
