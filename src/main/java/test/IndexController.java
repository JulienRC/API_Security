package test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
	
	@GetMapping(value="/") // URL
	public String index() {
		return "index"; // name of jsp page
	}
}
