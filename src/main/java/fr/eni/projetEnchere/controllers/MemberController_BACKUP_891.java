package fr.eni.projetEnchere.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import fr.eni.projetEnchere.bll.member.MemberService;
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.exception.UserNameAlreadyExistsException;
import fr.eni.projetEnchere.security.UserDetailsServiceImpl;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(MemberController.class);
	private MemberService service;

	public MemberController(MemberService service) {
		super();
		this.service = service;
	}

	@ModelAttribute
	public Member initializeMember() {
		Member m = new Member();
		m.setAdmin(false);
		m.setCredits(0);
		m.setEmail(null);
		m.setFirstName(null);
		m.setIdMember(0);
		m.setName(null);
		m.setPassword(null);
		m.setPhoneNumber(null);
		m.setRoadNumber(0);
		m.setRoadName(null);
		m.setTownName(null);
		m.setUserName(null);
		m.setZipCode(null);
		return m;

	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model, HttpSession session ) {
		Member member = (Member) session.getAttribute("loggedMember");
        if (member == null) {
            member = initializeMember();
        }
        model.addAttribute("member", member);
		return "register";
	}

	@PostMapping("/register")
	public String addMember(@Valid @ModelAttribute Member member, BindingResult result, RedirectAttributes redirectAttr,
			HttpSession session, Model model) {
		Member loggedMember = (Member) session.getAttribute("loggedMember");
		if (result.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.member", result);
			redirectAttr.addFlashAttribute("member", member);
			return "register";
		}
		 try {
			 	member.setIdMember(loggedMember.getIdMember());
		        session.setAttribute("loggedMember", member);
		        service.save(member, loggedMember);
		    } catch (UserNameAlreadyExistsException e) {
		        // Si l'exception est levée, on ajoute un message d'exception personnalisé à la vue
		        redirectAttr.addFlashAttribute("UserNameAlreadyExistsException", true);
		        redirectAttr.addFlashAttribute("ExceptionMessage", e.getMessage()); 
		        return "redirect:/register";
		    }
		 
		return "redirect:/home";

	}
<<<<<<< HEAD
	
	
	//blablabla test commit 
	
=======
	@GetMapping("/member/{id}")
	public String showMember(@PathVariable(name = "id") int id, Model model) {
		Member member;
		return "memberDetails";
		
	}
>>>>>>> cb06d389dcdb368041965130410860ab9cc264e1
	 @PostMapping("/deleteMember")
	    public String deleteAccount(HttpSession session) {
	        Member member = (Member) session.getAttribute("loggedMember");
	        logger.debug(member.toString());
	        if (member != null) {
	            service.delete(member.getIdMember());
	            logger.warn(member.toString());
	            session.invalidate();
	            
	        }
	        return "redirect:/home";
	    }

}
