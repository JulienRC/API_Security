package istic.m2cyber.vet.security_api.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthenticationController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
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

		// String user_id = (String)
		// ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("user_id");

		String url = "http://127.0.0.1:8080";
		RedirectView view = new RedirectView(url);
		return view;
	}

	private OAuth2User getCurrentUser(Authentication auth) {
		return ((OAuth2AuthenticationToken) auth).getPrincipal();
	}

}
