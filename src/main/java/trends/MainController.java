package trends;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {
	@RequestMapping("/")
	public String showPage(Model theModel) {
		
		
	
		return "signup";
	}
}
