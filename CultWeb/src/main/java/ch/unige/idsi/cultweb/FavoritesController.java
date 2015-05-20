package ch.unige.idsi.cultweb;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/favorites")
public class FavoritesController {

	@RequestMapping(method = RequestMethod.GET)
	public String showView(ModelMap model) throws IOException {
		return "favorites";
	}
}