package com.springboot.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/login1")
	public String loginMessage() {
		// return "Hello World";
		return "login";
	}

	@RequestMapping("/loginParm")
	// @ResponseBody
	public String loginMessageParm(@RequestParam String name, ModelMap model) {
		model.put("name", name);
		return "loginparam";
	}

	// Get
	@RequestMapping("/loginForm")
	public String loginMessageForm(ModelMap model) {
		// model.put("name", name);
		return "loginForm";
	}

	@RequestMapping("/loginPost")
	public String loginMessagePost(ModelMap model) {
		// model.put("name", name);
		return "loginPost";
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginGet(ModelMap model) {
		// model.put("name", name);
		return "loginPost";
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.POST)
	public String welcomePost(ModelMap model, @RequestParam String name) {
		model.put("input", name);
		return "welcome";
	}

}
