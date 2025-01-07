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
	public String showRegistrationForm(Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedMember");
		if (member == null) {
			member = initializeMember();
		}
		model.addAttribute("member", member);
		if ((Member)session.getAttribute("loggedMember") != null) {
		logger.debug("membre dans le model : " + ((Member)model.getAttribute("member")).toString());
		logger.debug("membre en session : " + ((Member)session.getAttribute("loggedMember")).toString());
		}
		return "register";
	}

	@PostMapping("/register")
	public String addMember(@Valid @ModelAttribute Member member, BindingResult result, RedirectAttributes redirectAttr,
			HttpSession session, Model model) {
		System.out.println("oui je passe dans le postmapping /register");
		Member loggedMember = (Member) session.getAttribute("member");
		if (loggedMember != null) {
			logger.debug(loggedMember.toString());
		} else {
			logger.debug("no logged member");

		}
		if (result.hasErrors()) {
			logger.debug("Validation errors: " + result.getAllErrors());
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.member", result);
			redirectAttr.addFlashAttribute("member", member);
			return "register";
		}
		try {
			if (loggedMember != null) {
				member.setIdMember(loggedMember.getIdMember());
				logger.debug(member.toString());
				Member UpdatedMember = service.getById(member.getIdMember());
				logger.debug(UpdatedMember.toString());
				model.addAttribute("member", UpdatedMember);
			}
			service.save(member, loggedMember);
			if (loggedMember == null) {
				logger.debug("membre créé : " + service.getByUserName(member.getUserName()).toString());
			}
		} catch (UserNameAlreadyExistsException e) {
			// Si l'exception est levée, on ajoute un message d'exception personnalisé à la
			// vue
			logger.debug("user already exists");
			redirectAttr.addFlashAttribute("UserNameAlreadyExistsException", true);
			redirectAttr.addFlashAttribute("ExceptionMessage", e.getMessage());
			return "redirect:/register";
		}
		logger.debug("Redirection to /home");
		return "redirect:/home";

	}

	@GetMapping("/member/{id}")
	public String showMember(@PathVariable(name = "id") int id, Model model) {
		Member member = service.getById(id);
		model.addAttribute("member", member);
		logger.debug(member.toString());
		return "member/memberDetails";

	}

	@PostMapping("/deleteMember")
	public String deleteAccount(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedMember");
		
		if (member != null) {
			logger.debug(member.toString());
			service.delete(member.getIdMember());
			logger.warn(member.toString());
			session.invalidate();

		}
		return "redirect:/home";
	}

}
