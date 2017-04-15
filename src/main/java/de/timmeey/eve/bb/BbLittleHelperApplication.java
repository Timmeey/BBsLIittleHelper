package de.timmeey.eve.bb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RequestAuthenticator;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.Base64;

@SpringBootApplication
@EnableOAuth2Client
@RestController
public class BbLittleHelperApplication extends WebSecurityConfigurerAdapter {
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	public static void main(String[] args) {
		SpringApplication.run(BbLittleHelperApplication.class, args);
	}




	@RequestMapping("/")
	public Principal main(Principal principal) {
		System.out.println(principal);
		return principal;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}

	private Filter ssoFilter(){
		OAuth2ClientAuthenticationProcessingFilter eveFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
		OAuth2RequestAuthenticator eveAuthenticator = new OAuth2RequestAuthenticator() {
			@Override
			public void authenticate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext clientContext, ClientHttpRequest request) {
				String auth = String.format("%s:%s",resource.getClientId(),resource.getClientSecret());
				String b64Auth = Base64.getEncoder().encodeToString(auth.getBytes());
				System.out.println(String.format("Basic %s", b64Auth));
				request.getHeaders().set("Authorization", String.format("Basic %s", b64Auth));

			}
		};
		OAuth2RestTemplate eveTemplate = new OAuth2RestTemplate(eve(),oauth2ClientContext);
		eveTemplate.setAuthenticator(eveAuthenticator);

		eveFilter.setRestTemplate(eveTemplate);
		UserInfoTokenServices tokenService = new UserInfoTokenServices(eveResource().getUserInfoUri(),eve().getClientId());
		tokenService.setRestTemplate(eveTemplate);
		tokenService.setRestTemplate(eveTemplate);
		eveFilter.setTokenServices(tokenService);
		return eveFilter;
	}

	@Bean
	@ConfigurationProperties("eve.client")
	public AuthorizationCodeResourceDetails eve(){
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("eve.resource")
	public ResourceServerProperties eveResource(){
		return new ResourceServerProperties();
	}
}

