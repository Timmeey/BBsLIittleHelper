package de.timmeey.eve.bb.OAuth2;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacter;
import de.timmeey.eve.bb.AuthenticatedCharacter.AuthenticatedCharacters;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Base64;
import java.util.Set;

/**
 * Created by timmeey on 16.04.17.
 */
@Slf4j
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
		log.debug("Redeeming Authorization code for {}", authorizationCode);
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
		log.debug("Obtaining AuthenticatedCharacter for {}", accessToken);

		Request request = new Request.Builder()
				.url(this.userInfoUri.toString())
				.addHeader("Authorization", String.format("Bearer %s", accessToken.getAccessTokenString()))
				.get()
				.build();
		Response response = assertResponse(httpClient.newCall(request).execute(), HttpURLConnection.HTTP_OK);

		OAuthCharacterDTO authCharDTO = gson.fromJson(response.body().charStream(), OAuthCharacterDTO.class);
		log.debug("Converted authCharDTO: {}", authCharDTO);
		val authenticatedCharacter = authenticatedCharacters.add(authCharDTO.getCharacterID(), authCharDTO
				.getCharacterName(), accessToken);

		return authenticatedCharacter;
	}

	private EveAccessToken convertResponseToAccessToken(Response response) throws IOException {
		AccessTokenDTO dto = gson.fromJson(response.body().string(), AccessTokenDTO.class);
		log.debug("Converted to accessToken: {}", dto);
		EveAccessToken accessToken = new EveAccessToken(dto, this);
		return accessToken;
	}

	private Response postRequestBodyAuthenticated(RequestBody body) throws IOException {
		Request request = new Request.Builder()
				.url(this.accessTokenURI.toString())
				.addHeader("Authorization", String.format("Basic %s", this.base64EncodedCredentials()))
				.post(body)
				.build();
		return assertResponse(httpClient.newCall(request).execute(), HttpURLConnection.HTTP_OK);
	}

	private Response assertResponse(Response response, int expectedCode) throws IOException {
		if (response.code() != expectedCode) {
			throw new IOException(String.format("Unexpected response code was %s instead of %s. Response was %s",
					response.code(), expectedCode, response.body().string()));
		}
		return response;
	}

	private String base64EncodedCredentials() {
		return Base64.getEncoder().encodeToString(String.format("%s:%s", this.clientId, this.clientSecret).getBytes());
	}

}
