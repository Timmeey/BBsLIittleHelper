package de.timmeey.eve.bb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
public class BbLittleHelperApplication{
	public static void main(String[] args) {
		SpringApplication.run(BbLittleHelperApplication.class, args);
	}




	@RequestMapping("/")
	public String main(Principal principal) {
		((FakeAuthenticatedCharacter) ((OAuth2Authentication) principal).getPrincipal()).doRefreshToken();
		return ((OAuth2Authentication) principal).getPrincipal().toString();
	}
}

