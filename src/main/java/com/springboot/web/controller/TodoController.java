package com.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.web.jpa.repositories.TodoRepository;
import com.springboot.web.model.Todo;
import com.springboot.web.service.TodoService;

@Controller
// @SessionAttributes("name")
public class TodoController {

	// @Autowired
	// TodoService todoService;

	@Autowired
	TodoRepository repository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "login";
	}

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public String showWelcomePage(ModelMap model, @RequestParam String name,
	// @RequestParam String password) {
	//
	// boolean isValidUser = todoService.validateUser(name, password);
	// System.out.println("Is Valid : " + isValidUser);
	//
	// if (!isValidUser) {
	// model.put("errorMessage", "Invalid Credentials");
	// return "welcome";
	// }
	//
	// model.put("name", name);
	// model.put("password", password);
	//
	// return "welcome";
	// }

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		String name = getLoggedInUserName();
		// model.put("todos", todoService.retrieveTodos("Hari"));
		// model.put("todos", todoService.retrieveTodos(name));
		model.put("todos", repository.findByUser(name));
		return "list-todos";
	}

	// private String getLoggedInUserName(ModelMap model) {
	// return (String) model.get("name");
	// }

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(), "Default Desc", new Date(), false));
		return "todo";
	}

	// @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	// public String addTodo(ModelMap model, @RequestParam String desc) {
	// todoService.addTodo((String) model.get("name"), desc, new Date(), false);
	// return "redirect:/list-todos";
	// }

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
		// if (id == 1) {
		// throw new RuntimeException("Something went Wrong");
		// }

		// todoService.deleteTodo(id);
		repository.deleteById(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		// todoService.addTodo(getLoggedInUserName(), todo.getDesc(), new Date(),
		// false);

		todo.setUser(getLoggedInUserName());
		repository.save(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		// Todo todo = todoService.retrieveTodo(id);
		Optional<Todo> todo = repository.findById(id);
		model.put("todo", todo.get());
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}
		todo.setUser(getLoggedInUserName());
		// todoService.updateTodo(todo);
		repository.save(todo);
		return "redirect:/list-todos";
	}
}
