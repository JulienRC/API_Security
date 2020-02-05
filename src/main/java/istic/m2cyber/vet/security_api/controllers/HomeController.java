package istic.m2cyber.vet.security_api.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({ "/" })
	public String home(Authentication authentication, Model model) {

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
		return "home";
	}

}
