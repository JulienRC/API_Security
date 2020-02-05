package istic.m2cyber.vet.security_api.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import istic.m2cyber.vet.security_api.models.Log;
import istic.m2cyber.vet.security_api.models.User;
import istic.m2cyber.vet.security_api.service.LogService;
import istic.m2cyber.vet.security_api.service.UserService;
import istic.m2cyber.vet.security_api.utils.Utils;

@Controller
public class AuthenticationController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private LogService logservice;
	
	private Utils utils;
	
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String client_id;
	
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String client_secret;
	
	private final OAuth2AuthorizedClientService authorizedClientService;

	public AuthenticationController(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Authentication authentication, Model model) {

		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {

			OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();

			model.addAttribute("first_name", user.getAttribute("given_name"));
			model.addAttribute("last_name", user.getAttribute("family_name"));
			model.addAttribute("email", user.getAttribute("email"));
			model.addAttribute("picture", user.getAttribute("picture"));
			model.addAttribute("isConnected", true);
		}
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public RedirectView logout(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {

		if (authentication != null)
			new SecurityContextLogoutHandler().logout(request, response, authentication);

		String url = "http://127.0.0.1:8080/";
		RedirectView view = new RedirectView(url);

		return view;
	}

	@RequestMapping(value = "/check_user_in_database", method = RequestMethod.GET)
	public RedirectView checkUserInDatabase(Authentication authentication) {
		utils = new Utils();
		 String user_id = (String)
		 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("sub");
		
		 User u = userservice.findByGoogleid(utils.StringInHashWithSalt(user_id));
		 LocalDateTime now = LocalDateTime.now();  
        
		if( u == null) {
			String email = (String)
					 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("email");
			
			userservice.save(new User(utils.StringInHashWithSalt(email),utils.StringInHashWithSalt(user_id)));
			
			logservice.save(new Log(utils.StringInHashWithSalt(user_id),now));
			
		}
		else { 
			logservice.save(new Log(utils.StringInHashWithSalt(user_id),now));
		}
		String url = "http://127.0.0.1:8080";
		RedirectView view = new RedirectView(url);
		return view;
	}
	
	private List<PhoneNumber> getPhoneNumbers(Authentication authentication) throws IOException {
		OAuth2AuthenticationToken oauth2AuthToken = ((OAuth2AuthenticationToken) authentication);

		OAuth2AccessToken access_token = this.authorizedClientService
				.loadAuthorizedClient(oauth2AuthToken.getAuthorizedClientRegistrationId(), oauth2AuthToken.getName())
				.getAccessToken();

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport())
				.setJsonFactory(new JacksonFactory()).setClientSecrets(client_id, client_secret).build()
				.setAccessToken(access_token.getTokenValue());

		PeopleService peopleService = new PeopleService.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();

		Person profile = peopleService.people().get("people/me").setRequestMaskIncludeField("person.phone_numbers").execute();

		List<PhoneNumber> phoneList = profile.getPhoneNumbers();
		return phoneList;
	}

}
