package ch.unige.idsi.cultweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ch.unige.idsi.cultweb.api.ApiRequest;
import ch.unige.idsi.cultweb.model.Place.Infrastructure;

@Controller
public class ApiController {
	
	@RequestMapping("/api")
	public ModelAndView getRoot() throws IOException {
		return new ModelAndView("api", "response", "Welcome");
	}
	
	/**
	 * Fetches all the museums
	 * 
	 * @return : Museums parsed in JSON format
	 * @throws IOException
	 */
	@RequestMapping("/api/museum")
	public ModelAndView getMuseums() throws IOException {
		ApiRequest req = new ApiRequest();
		String response = req.getMuseums().toString();
		return new ModelAndView("api", "response", response);
	}

	/**
	 * Fetches all the cinemas
	 * 
	 * @return : Cinemas parsed in JSON format
	 * @throws IOException
	 */
	@RequestMapping("/api/cinema")
	public ModelAndView getCinemas() throws IOException {
		ApiRequest req = new ApiRequest();
		String response = req.getCinemas().toString();
		return new ModelAndView("api", "response", response);
	}

	@RequestMapping(value = "/api/museum/{id:[\\d]+}")
	public ModelAndView getMuseumInfo(@PathVariable String id)
			throws IOException {
		ApiRequest req = new ApiRequest();
		String response = req.getInfo(id, Infrastructure.MUSEUM).toString();
		return new ModelAndView("api", "response", response);
	}

	@RequestMapping(value = "/api/cinema/{id:[\\d]+}")
	public ModelAndView getCinemaInfo(@PathVariable String id)
			throws IOException {
		ApiRequest req = new ApiRequest();
		String response = req.getInfo(id, Infrastructure.CINEMA).toString();
		return new ModelAndView("api", "response", response);
	}
	
	@RequestMapping(value = "/api/museum/rss/{id:[\\d]+}")
	public ModelAndView getMuseumRSS(@PathVariable String id)
			throws IOException {
		ApiRequest req = new ApiRequest();
		String response = req.getInfo(id, Infrastructure.MUSEUM).toString();
		return new ModelAndView("api", "response", response);
	}

	@RequestMapping(value = "/api/cinema/rss/{id:[\\d]+}")
	public ModelAndView getCinemaRSS(@PathVariable String id)
			throws IOException {
		ApiRequest req = new ApiRequest();
		String response = req.getInfo(id, Infrastructure.CINEMA).toString();
		return new ModelAndView("api", "response", response);
	}
}
