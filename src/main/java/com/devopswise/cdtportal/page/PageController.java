package com.devopswise.cdtportal.page;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String landingPage(Map<String, Object> model) {
		model.put("message", "Landing Page");

		// Return the landing page
		return "landing";
	}
	
	@RequestMapping("/workspace")
	public String workspacePage(Map<String, Object> model) {
		model.put("message", "workspace page");

		// Return the landing page
		return "workspace";
	}
}
