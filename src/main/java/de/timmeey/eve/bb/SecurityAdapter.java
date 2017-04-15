package de.timmeey.eve.bb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by timmeey on 15.04.17.
 */
@Configuration
@Order(1)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
	@Autowired
	EveSSOProviderImpl eveSSOProvider;



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and().addFilterBefore(eveSSOProvider.ssoFilter(), BasicAuthenticationFilter.class);
	}






}
