package de.timmeey.eve.bb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import java.util.Map;

/**
 * Created by timmeey on 15.04.17.
 */
@EnableOAuth2Client
@Configuration
@Order(1)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	private OAuth2RestTemplate eveTemplate;






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
		this.eveTemplate = new OAuth2RestTemplate(eve(),oauth2ClientContext);
		OAuth2ClientAuthenticationProcessingFilter eveFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
		eveFilter.setRestTemplate(eveTemplate);
		UserInfoTokenServices tokenService = new UserInfoTokenServices(eveResource().getUserInfoUri(),eve().getClientId());
		tokenService.setPrincipalExtractor(new PrincipalExtractor() {
			@Override
			public Object extractPrincipal(final Map<String, Object> map) {
				System.out.println(map.get("Credentials"));
				System.out.println(oauth2ClientContext.getAccessToken().getRefreshToken().getValue());
				return map;
			}
		});
		eveFilter.setTokenServices(tokenService);
		return eveFilter;
	}

	@Bean
	@ConfigurationProperties("eve.client")
	protected AuthorizationCodeResourceDetails eve(){
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("eve.resource")
	protected ResourceServerProperties eveResource(){
		return new ResourceServerProperties();
	}

	@Bean
	protected FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter)
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

}
