package de.timmeey.eve.bb.OAuth2;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.net.URI;
import java.util.Set;

public class EveOAuth2ApiImplBuilder {
	private String clientId;
	private String clientSecret;
	private URI accessTokenURI;
	private URI userAuthorizationUri;
	private Set<String> scopes;
	private URI userInfoUri;
	private String redirectToHost;
	private OkHttpClient httpClient;
	private Gson gson;

	public EveOAuth2ApiImplBuilder setClientId(final String clientId) {
		this.clientId = clientId;
		return this;
	}

	public EveOAuth2ApiImplBuilder setClientSecret(final String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

	public EveOAuth2ApiImplBuilder setAccessTokenURI(final URI accessTokenURI) {
		this.accessTokenURI = accessTokenURI;
		return this;
	}

	public EveOAuth2ApiImplBuilder setUserAuthorizationUri(final URI userAuthorizationUri) {
		this.userAuthorizationUri = userAuthorizationUri;
		return this;
	}

	public EveOAuth2ApiImplBuilder setScopes(final Set<String> scopes) {
		this.scopes = scopes;
		return this;
	}

	public EveOAuth2ApiImplBuilder setScopes(final String scopes) {
		return setScopes(Sets.newHashSet(scopes.split(" ")));
	}

	public EveOAuth2ApiImplBuilder setUserInfoUri(final URI userInfoUri) {
		this.userInfoUri = userInfoUri;
		return this;
	}

	public EveOAuth2ApiImplBuilder setRedirectToHost(final String redirectToHost) {
		this.redirectToHost = redirectToHost;
		return this;
	}

	public EveOAuth2ApiImplBuilder setHttpClient(final OkHttpClient httpClient) {
		this.httpClient = httpClient;
		return this;
	}

	public EveOAuth2ApiImplBuilder setGson(final Gson gson) {
		this.gson = gson;
		return this;
	}

	public EveOAuth2ApiImpl createEveOAuth2ApiImpl() {
		return new EveOAuth2ApiImpl(clientId, clientSecret, accessTokenURI, userAuthorizationUri, scopes, userInfoUri,
				redirectToHost, httpClient, gson);
	}
}