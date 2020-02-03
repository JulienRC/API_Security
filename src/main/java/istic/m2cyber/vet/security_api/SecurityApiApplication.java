package istic.m2cyber.vet.security_api;

import java.security.Principal;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import istic.m2.cyber.vet.security_api.service.UserService;
import istic.m2cyber.vet.security_api.models.User;

@SpringBootApplication
@Controller
public class SecurityApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApiApplication.class, args);
	}
	
	@RequestMapping({"/", "/home"})
	public String home(Principal principal, Model model) {
		UserService userservi = new UserService();
		Optional<User> u = userservi.findByGoogleid("1256");
		if( u == null) {
	    System.out.println("YAS");
		}
		
		if (principal == null) {
			model.addAttribute("isConnected", false);
			
		}else
			model.addAttribute("isConnected", true);
		return "Home";
	}
	
	@RequestMapping("/index")
	public String index(Principal principal, Model model) {
		if (principal == null)
			model.addAttribute("isConnected", false);
		else
			model.addAttribute("isConnected", true);
		return "Index";
	}
	
	@RequestMapping("/log")
	public String log(Principal principal, Model model) {
		if (principal == null)
			model.addAttribute("isConnected", false);
		else
			model.addAttribute("isConnected", true);
		return "Log";
	}
	
	
	
	

}
