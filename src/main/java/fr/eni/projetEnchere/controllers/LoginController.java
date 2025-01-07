package fr.eni.projetEnchere.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.projetEnchere.bll.member.MemberService;
import fr.eni.projetEnchere.bo.Member;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserDetailsService service;
	MemberService memberService;
	
	public LoginController(UserDetailsService service, MemberService memberService) {
		super();
		this.service = service;
		this.memberService = memberService;
	}

	// password for bob_jones : mySecret789
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    @GetMapping({"/", "/home", "/encheres"})

    public String home(HttpSession session ) {
    	// Get the currently authenticated user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            Member member = memberService.getByUserName(username);
            // You can use the userDetails here
            session.setAttribute("loggedMember", member);
        }
//    	session.setAttribute("loggedMember", );
    	return "redirect:/article/loadArticles";
    }
    
//    @PostMapping("/logout")
//    public String logout(HttpSession session, Model model) {
//    	logger.debug("logging out");
//		return "/home";    	
//   }
    
}