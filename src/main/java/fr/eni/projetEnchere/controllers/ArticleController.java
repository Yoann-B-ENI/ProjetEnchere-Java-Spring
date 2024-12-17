package fr.eni.projetEnchere.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/redirectToCreate")
	public String redirectToCreate(Model model) {
		System.out.println("Redirecting to create article form");
		
		List<Category> categoriesFound = categoryService.getAll();
		model.addAttribute("allCategories", categoriesFound);
		System.out.println(categoriesFound);
		
		List<RemovalPoint> removalPointsFound = removalPointService.getAll();
		model.addAttribute("allRemovalPoints", removalPointsFound);
		System.out.println(removalPointsFound);
		
		return "article/formCreateArticle";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute Article article, Model model) {
		System.out.println("Sending article to db " + article);
		
		articleService.determineStatusFromDates(article);
		article.setVendor(999);
		// get member
		// insert member's adress as a removalpoint
		// make removalpoint user dependant
		// add removalpoints in create article form
		articleService.create(article);
		
		return "/";
	}
	
	
	
	
	
}
