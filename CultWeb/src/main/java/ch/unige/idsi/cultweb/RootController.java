package ch.unige.idsi.cultweb;

import java.io.IOException;
import java.util.ArrayList;

import ch.unige.idsi.cultweb.api.ApiRequest;
import ch.unige.idsi.cultweb.model.Museum;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws IOException {

		model.addAttribute("museums", this.getObjectMuseums());
		model.addAttribute("cinemas", this.getObjectCinemas());
		return "root";
	}

	private ArrayList<Museum> getObjectMuseums() throws IOException {

		ArrayList<Museum> museums = new ArrayList<Museum>();

		ApiRequest req = new ApiRequest();

		JSONArray array = req.getMuseums();

		for (int i = 0; i < array.size(); i++) {

			JSONObject obj = (JSONObject) array.get(i);
			JSONObject attributes = (JSONObject) obj.get("attributes");

			long id = (((Long) attributes.get("ID_INFRASTRUCTURE")).intValue());
			String name = (String) attributes.get("NOM");
			String town = (String) attributes.get("COMMUNE");
			String address = (String) attributes.get("ADRESSE");
			String url = (String) attributes.get("LIEN_WWW");

			Museum m = new Museum(id, name, town, address, url, 0, 0);

			museums.add(m);
		}
		return museums;
	}

	private ArrayList<Museum> getObjectCinemas() throws IOException {

		ArrayList<Museum> cinemas = new ArrayList<Museum>();

		ApiRequest req = new ApiRequest();

		JSONArray array = req.getCinemas();

		for (int i = 0; i < array.size(); i++) {

			JSONObject obj = (JSONObject) array.get(i);
			JSONObject attributes = (JSONObject) obj.get("attributes");

			long id = (((Long) attributes.get("ID_INFRASTRUCTURE")).intValue());
			String name = (String) attributes.get("NOM");
			String town = (String) attributes.get("COMMUNE");
			String address = (String) attributes.get("ADRESSE");
			String url = (String) attributes.get("LIEN_WWW");

			Museum m = new Museum(id, name, town, address, url, 0, 0);

			cinemas.add(m);
		}
		return cinemas;
	}
}