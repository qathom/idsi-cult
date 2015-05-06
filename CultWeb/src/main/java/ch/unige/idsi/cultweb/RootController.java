package ch.unige.idsi.cultweb;

import java.io.IOException;

import ch.unige.idsi.cultweb.api.DataAccessObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class RootController {

	private DataAccessObject dao;
	
	/**
	 * Creathe DAO object to keep objects in memory
	 * @constructor
	 * @throws IOException
	 */
	public RootController() throws IOException {
		dao = new DataAccessObject();
	}
	
	/**
	 * Displays the root website
	 * @param model : cinemas and museums
	 * @return : the JSP page root
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws IOException {
		model.addAttribute("museums", dao.getMuseums());
		model.addAttribute("cinemas", dao.getCinemas());
		
		return "root";
	}
}