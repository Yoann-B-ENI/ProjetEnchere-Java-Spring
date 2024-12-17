package fr.eni.projetEnchere.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projetEnchere.bll.article.ArticleService;
import fr.eni.projetEnchere.bll.category.CategoryService;
import fr.eni.projetEnchere.bll.removalpoint.RemovalPointService;
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Category;
import fr.eni.projetEnchere.bo.RemovalPoint;
import fr.eni.projetEnchere.controllers.converters.CustomCategoryEditor;
import fr.eni.projetEnchere.controllers.converters.CustomRemovalPointEditor;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	ArticleService articleService;
	CategoryService categoryService;
	RemovalPointService removalPointService;
	
	@Autowired
	public ArticleController(CategoryService categoryService, RemovalPointService removalPointService, 
			ArticleService articleService) {
		super();
		this.categoryService = categoryService;
		this.removalPointService = removalPointService;
		this.articleService = articleService;
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder, HttpSession session) {
		System.out.println("init binder called, binding category");
        // Use the cached list from session, since we can't pass Model as an argument
		List<Category> allCategories = (List<Category>) session.getAttribute("allCategories");
        binder.registerCustomEditor(Category.class, new CustomCategoryEditor(allCategories));
        
        System.out.println("> > now binding removalPoint");
        List<RemovalPoint> allRemovalPoints = (List<RemovalPoint>) session.getAttribute("allRemovalPoints");
        binder.registerCustomEditor(RemovalPoint.class, new CustomRemovalPointEditor(allRemovalPoints));
    }
	
	@GetMapping("/redirectToCreate")
	public String redirectToCreate(Model model, HttpSession session) {
		System.out.println("Redirecting to create article form");
		
		List<Category> categoriesFound = categoryService.getAll();
		// model for the html, session for the 1-database-call editor in the init binder
		session.setAttribute("allCategories", categoriesFound); // session so make sure to overwrite it every form
		model.addAttribute("allCategories", categoriesFound);
		System.out.println(categoriesFound);
		
		List<RemovalPoint> removalPointsFound = removalPointService.getAll();
		session.setAttribute("allRemovalPoints", removalPointsFound);
		model.addAttribute("allRemovalPoints", removalPointsFound);
		System.out.println(removalPointsFound);
		
		return "article/formCreateArticle";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute Article article, Model model) {
		System.out.println("Sending article to db " + article);
		
		articleService.determineStatusFromDates(article);
		article.setVendor(1); //TODO change please please
		// get member
		// insert member's adress as a removalpoint
		// make removalpoint user dependant
		// add removalpoints in create article form
		articleService.create(article);
		
		return "/";
	}
	
	
	
	
	
}
