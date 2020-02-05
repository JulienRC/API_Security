package istic.m2cyber.vet.security_api.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LinkController {

	@GetMapping({ "/team" })
	public String team(Authentication authentication, Model model) {
		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {
			model.addAttribute("isConnected", true);
		}
		return "team";
	}
	
	@GetMapping({ "/company" })
	public String company(Authentication authentication, Model model) {
		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {
			model.addAttribute("isConnected", true);
		}
		return "company";
	}
	
	@GetMapping({ "/project" })
	public String project(Authentication authentication, Model model) {
		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {
			model.addAttribute("isConnected", true);
		}
		return "project";
	}
	
}