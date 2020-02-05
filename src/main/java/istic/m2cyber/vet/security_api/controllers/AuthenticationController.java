package istic.m2cyber.vet.security_api.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

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

	private final OAuth2AuthorizedClientService authorizedClientService;

	public AuthenticationController(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
		this.utils = new Utils();
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Authentication authentication, Model model) {

		// If the visitor isn't authenticate then we indicate it.
		if (authentication == null)
			model.addAttribute("isConnected", false);

		else {
			
			// We get user object in order to get attributes.
			OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();

			/** Remove attributes that are not used in html **/
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

		// Allows to disconnect from the session.
		if (authentication != null)
			new SecurityContextLogoutHandler().logout(request, response, authentication);

		// Allows to get base of url (ex.: http://example.com/).
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

		return new RedirectView(baseUrl);
	}

	@RequestMapping(value = "/check_user_in_database", method = RequestMethod.GET)
	public RedirectView checkUserInDatabase(Authentication authentication) throws IOException {

		String user_id = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes()
				.get("sub");

		User user = this.userservice.findByGoogleid(this.utils.StringInHashWithSalt(user_id));

		LocalDateTime now = LocalDateTime.now();

		if (user == null) {
			String email = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes()
					.get("email");

			List<PhoneNumber> listetel = this.utils.getPhoneNumbers(authentication, this.authorizedClientService);

			if (listetel == null)
				this.userservice.save(
						new User(this.utils.StringInHashWithSalt(email), this.utils.StringInHashWithSalt(user_id)));
			else {
				String tel = listetel.get(0).getValue();
				this.userservice.save(new User(this.utils.StringInHashWithSalt(email),
						this.utils.StringInHashWithSalt(user_id), this.utils.StringInHashWithSalt(tel)));
			}
			this.logservice.save(new Log(this.utils.StringInHashWithSalt(user_id), now));

		} else
			this.logservice.save(new Log(this.utils.StringInHashWithSalt(user_id), now));

		// Allows to get base of url (ex.: http://example.com/).
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

		return new RedirectView(baseUrl);
	}

}
