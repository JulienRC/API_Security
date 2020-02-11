package istic.m2cyber.vet.security_api.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import istic.m2cyber.vet.security_api.models.Log;
import istic.m2cyber.vet.security_api.service.LogService;
import istic.m2cyber.vet.security_api.utils.Utils;

@Controller
public class LogController {

	@Autowired
	private LogService logService;

	private Utils utils;

	public LogController() {

		utils = new Utils();
	}

	@GetMapping({ "/log" })
	public String log(Authentication authentication, Model model) {

		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {

			OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();

			model.addAttribute("first_name", user.getAttribute("given_name"));
			model.addAttribute("last_name", user.getAttribute("family_name"));
			model.addAttribute("email", user.getAttribute("email"));
			model.addAttribute("picture", user.getAttribute("picture"));
			model.addAttribute("isConnected", true);

			List<Log> listLogDB = logService.findByGoogleid(utils.StringInHashWithSalt((String) user.getAttribute("sub")));
			List<String> list = new ArrayList<String>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			for (Log log : listLogDB) {
				list.add(log.getDate().format(formatter));
			}
			model.addAttribute("logs", list);
		}
		return "log";
	}
}
