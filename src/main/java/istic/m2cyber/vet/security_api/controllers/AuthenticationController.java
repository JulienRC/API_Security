package istic.m2cyber.vet.security_api.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import istic.m2cyber.vet.security_api.models.Log;
import istic.m2cyber.vet.security_api.models.User;
import istic.m2cyber.vet.security_api.service.LogService;
import istic.m2cyber.vet.security_api.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private LogService logservice;

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

		 String user_id = (String)
		 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("user_id");
		System.out.println(user_id);
		User u = userservice.findByGoogleid(user_id);
		if( u == null) {
			String email = (String)
					 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("email");
			
			String picture = (String)
					 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("picture");

					
			userservice.save(new User(email,user_id,picture));
			//logservice.save(new Log());
			
		}
		String url = "http://127.0.0.1:8080";
		RedirectView view = new RedirectView(url);
		return view;
	}

}
