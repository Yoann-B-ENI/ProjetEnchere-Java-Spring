package fr.eni.projetEnchere.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.projetEnchere.bll.category.CategoryService;
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Category;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	CategoryService categoryService;
	
	@Autowired
	public ArticleController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping("/redirectToCreate")
	public String redirectToCreate(Model model) {
		
		// donner nom
		// donner description
		// choisir categorie
		List<Category> categoriesFound = categoryService.getAll();
		model.addAttribute("allCategories", categoriesFound);
		System.out.println(categoriesFound);
		// donner prix de departs (pts)
		// choisir date et heure ouverture
		// choisir d h fermeture
		// choisir point de retrait, par défaut adresse du vendeur
		
		return "templates/article/formCreateArticle";
	}
	
	@PostMapping("/create")
	public String create(Article article) {
		
		return "aa";
	}
	
	
	
	
	
}
