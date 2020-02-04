package istic.m2cyber.vet.security_api;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import istic.m2.cyber.vet.security_api.service.LogService;
import istic.m2.cyber.vet.security_api.service.UserService;
import istic.m2cyber.vet.security_api.models.User;
import istic.m2cyber.vet.security_api.models.Log;


@ComponentScan("istic.m2.cyber.vet.security_api")
@SpringBootApplication
@Controller
public class SecurityApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApiApplication.class, args);
	}
	
	@Autowired
	private UserService userservi;
	
	@Autowired
	private LogService logservice;
	
	@RequestMapping({"/", "/home"})
	public String home(Principal principal, Model model) {
		
		List<Log> liste = logservice.findByGoogleid("1256");
		for( Log l : liste) {
			System.out.println(l.getDate());
		}
		
		//System.out.println(liste.getDate());
		
		/*User u = userservi.findByGoogleid("1256");
		if( u == null) {
	    System.out.println("YAS");
		}else { System.out.println("YASSSSSSSSSSss"); }*/
		
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
