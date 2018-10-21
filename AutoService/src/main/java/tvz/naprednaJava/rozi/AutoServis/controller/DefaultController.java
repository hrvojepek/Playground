package tvz.naprednaJava.rozi.AutoServis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	@RequestMapping({ "/index", "/" })
	public String getIndex(Model model) {
		return "index";
	}

	@RequestMapping("/private")
	public String getPrivateIndex(Model model) {
		return "index";
	}
}
