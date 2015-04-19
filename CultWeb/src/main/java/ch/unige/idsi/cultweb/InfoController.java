package ch.unige.idsi.cultweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.unige.idsi.cultweb.model.Feed;
import ch.unige.idsi.cultweb.model.FeedMessage;
import ch.unige.idsi.cultweb.model.Museum;
import ch.unige.idsi.cultweb.model.Place.Infrastructure;
import ch.unige.idsi.cultweb.util.RSSFeedParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/info")
public class InfoController {

	@RequestMapping(value = "/{infrastructure}/{id:[\\d]+}", method = RequestMethod.GET)
	public String printWelcome(ModelMap model,
			@PathVariable String infrastructure, @PathVariable String id)
			throws IOException {

		Infrastructure infra = null;

		if (infrastructure.equals(Infrastructure.CINEMA.toString()
				.toLowerCase())) {
			infra = Infrastructure.CINEMA;
		} else if (infrastructure.equals(Infrastructure.MUSEUM.toString()
				.toLowerCase())) {
			infra = Infrastructure.MUSEUM;
		}

		if (infra != null) {

			ApiRequest req = new ApiRequest();

			JSONObject res = req.getInfo(id, infra);
			
			model.addAttribute("id", id);
			model.addAttribute("name", (String) res.get("NOM"));

			model.addAttribute("contact", (String) res.get("CONTACT"));
			model.addAttribute("officialsite", (String) res.get("LIEN_WWW"));
			model.addAttribute("address", (String) res.get("ADRESSE"));
			model.addAttribute("town", (String) res.get("COMMUNE"));

			model.addAttribute("latitude", (double) res.get("latitude"));
			model.addAttribute("longitude", (double) res.get("longitude"));

			System.out.println("!!");
			System.out.println(res.toString());

			List<String> list = new ArrayList<String>();
			JSONArray links = (JSONArray) res.get("links");

			if (links != null) {
				for (int i = 0; i < links.size(); i++) {
					list.add((String) links.get(i));
				}
			}

			model.addAttribute("links", list);

			/*
			 * try {
			 * 
			 * String url = (String) res.get("LIEN_WWW"); Document doc =
			 * Jsoup.connect(url).get(); Elements links =
			 * doc.select("link[type=application/rss+xml]");
			 * 
			 * System.out.println("WWWW: " + url);
			 * System.out.println(doc.toString());
			 * 
			 * if (links.size() > 0) {
			 * 
			 * String rssUrl = links.get(0).attr("abs:href").toString();
			 * 
			 * System.out.println("RSS URL FOUND: " + rssUrl);
			 * 
			 * RSSFeedParser parser = new RSSFeedParser(rssUrl); Feed feed =
			 * parser.readFeed(); System.out.println(feed); for (FeedMessage
			 * message : feed.getMessages()) { System.out.println("!! " +
			 * message);
			 * 
			 * }
			 * 
			 * } else { // RSS url not found
			 * System.out.println("RSS NOT FOUND"); } } catch(IOException e) {
			 * 
			 * }
			 */

			return "info";

		} else {

			return "erreur!";
		}
	}
}