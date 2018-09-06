package trends;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class MainController {
	@RequestMapping("/")
	public String showPage(Model theModel) {

		return "vote";
	}
	
	@RequestMapping(value="/vote" , method = RequestMethod.POST)
	public String vote(HttpServletRequest request) {
		String name = request.getParameter("username");
		String state = request.getParameter("State");
		String loksabha = request.getParameter("Loksabha");
		String mp = request.getParameter("MP");
		String assembly = request.getParameter("Assembly");
		String mla = request.getParameter("MLA");
		VoteDataBase.vote(name, state, loksabha, mp, assembly, mla);
		return "homepage";
	}
	
	@RequestMapping(value="/vote" , method = RequestMethod.GET)
	public String votePage() {
		return "vote";
	}
}
