package istic.m2cyber.vet.security_api.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

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
	
	public String StringInHashWithSalt(String string){
        String output = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            output = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return output;
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
		 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("sub");
		
		 User u = userservice.findByGoogleid(StringInHashWithSalt(user_id));
		 LocalDateTime now = LocalDateTime.now();  
        
		if( u == null) {
			String email = (String)
					 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("email");
			
			String picture = (String)
					 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("picture");

					
			userservice.save(new User(StringInHashWithSalt(email),StringInHashWithSalt(user_id),StringInHashWithSalt(picture)));
			
			logservice.save(new Log(StringInHashWithSalt(user_id),now));
			
		}
		else { 
			logservice.save(new Log(StringInHashWithSalt(user_id),now));
		}
		String url = "http://127.0.0.1:8080";
		RedirectView view = new RedirectView(url);
		return view;
	}

}
