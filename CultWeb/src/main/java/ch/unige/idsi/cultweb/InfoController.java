package ch.unige.idsi.cultweb;

import java.io.IOException;

import ch.unige.idsi.cultweb.api.DataAccessObject;
import ch.unige.idsi.cultweb.model.Place;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/info")
public class InfoController {
	
	private DataAccessObject dao;
	
	public InfoController() throws IOException {
		dao = new DataAccessObject();
	}

	@RequestMapping(value = "/{infrastructure}/{id:[\\d]+}", method = RequestMethod.GET)
	public String printWelcome(ModelMap model,
			@PathVariable String infrastructure, @PathVariable String id)
			throws IOException {

		Place place = dao.getPlace(Long.valueOf(id), infrastructure);
		
		if(place != null) {
			model.addAttribute("museum", place);
			return "info";
		}
		return "404";
	}
}