package ch.unige.idsi.cultweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ch.unige.idsi.cultweb.api.DataAccessObject;
import ch.unige.idsi.cultweb.api.DataRequest;
import ch.unige.idsi.cultweb.model.Cinema;
import ch.unige.idsi.cultweb.model.Museum;
import ch.unige.idsi.cultweb.model.Place;
import ch.unige.idsi.cultweb.model.Place.Infrastructure;

@Controller
public class ApiController {
	
	private DataAccessObject dao;
	
	public ApiController() throws IOException {
		dao = new DataAccessObject();
	}
	
	@RequestMapping("/api")
	public ModelAndView getRoot() throws IOException {
		
		String msg = "<h2>Welcome to the CultWeb API</h2>";
		msg += "<pre><code>";
		msg += "[GET]	<a href=\"museums/\">/museums/</a>	=> get all the museums";
		msg += "\n";
		msg += "[GET]	<a href=\"cinemas/\">/cinemas/</a>	=> get all the cinemas";
		msg += "\n";
		msg += "[GET]	<a href=\"museum/{ID}\">/museum/{ID}/</a>	=> get all the information of a given museum ID";
		msg += "\n";
		msg += "[GET]	<a href=\"cinema/{ID}\">/cinema/{ID}/</a>	=> get all the information of a given cinema ID";
		msg += "</pre></code>";
		
		return new ModelAndView("api", "response", msg);
	}
	
	/**
	 * Fetches all the museums
	 * 
	 * @return : Museums parsed in JSON format
	 * @throws IOException
	 */
	@RequestMapping("/api/museums")
	public ModelAndView getMuseums() throws IOException {
		DataRequest req = new DataRequest();
		String response = req.getMuseums().toString();
		return new ModelAndView("api", "response", response);
	}

	/**
	 * Fetches all the cinemas
	 * 
	 * @return : Cinemas parsed in JSON format
	 * @throws IOException
	 */
	@RequestMapping("/api/cinemas")
	public ModelAndView getCinemas() throws IOException {
		DataRequest req = new DataRequest();
		String response = req.getCinemas().toString();
		return new ModelAndView("api", "response", response);
	}

	@RequestMapping(value = "/api/museum/{id:[\\d]+}")
	public ModelAndView getMuseumInfo(@PathVariable String id)
			throws IOException {

		Museum museum = (Museum) dao.getPlace(Long.valueOf(id), Infrastructure.MUSEUM.toString());
		
		if(museum != null) {
			
			JSONObject res = museum.toJSON();
			res.put("status", "success");

			return new ModelAndView("api", "response", res);
		} else {
			return new ModelAndView("api", "response", this.generateError().toString());
		}
	}

	@RequestMapping(value = "/api/cinema/{id:[\\d]+}")
	public ModelAndView getCinemaInfo(@PathVariable String id)
			throws IOException {
		
		Cinema cinema = (Cinema) dao.getPlace(Long.valueOf(id), Infrastructure.CINEMA.toString());
		
		if(cinema != null) {
			
			JSONObject res = cinema.toJSON();
			res.put("status", "success");
			
			return new ModelAndView("api", "response", res);
		} else {
			return new ModelAndView("api", "response", this.generateError().toString());
		}
	}
	
	private JSONObject generateError() {
		JSONObject res = new JSONObject();
		res.put("message", "INVALID_OBJECT_ID");
		res.put("status", "error");
		
		return res;
	}
}
