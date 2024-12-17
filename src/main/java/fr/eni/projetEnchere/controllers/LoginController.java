package fr.eni.projetEnchere.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.projetEnchere.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	UserDetailsService service;
	
	public LoginController(UserDetailsService service) {
		super();
		this.service = service;
	}

	// password for bob_jones : mySecret789
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    @GetMapping({"/", "/home", "/encheres"})
    public String home(HttpSession session) {
    	session.setAttribute("loggedMember", ((UserDetailsServiceImpl) service).getMember());
    	return "encheres";
    }
    
    
}