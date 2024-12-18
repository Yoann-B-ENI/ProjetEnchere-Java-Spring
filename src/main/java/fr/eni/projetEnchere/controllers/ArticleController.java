package fr.eni.projetEnchere.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.bo.RemovalPoint;
import fr.eni.projetEnchere.controllers.converters.CustomCategoryEditor;
import fr.eni.projetEnchere.controllers.converters.CustomRemovalPointEditor;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
		System.out.println("\n Redirecting to create article form");
		
		List<Category> categoriesFound = categoryService.getAll();
		// model for the html, session for the 1-database-call editor in the init binder
		session.setAttribute("allCategories", categoriesFound); // session so make sure to overwrite it every form
		model.addAttribute("allCategories", categoriesFound);
		System.out.println(categoriesFound);
		
		Member member = (Member) session.getAttribute("loggedMember");
		RemovalPoint rp = new RemovalPoint(0, 
				member.getRoadNumber(), member.getRoadName(), member.getZipCode(), member.getTownName(), member, 
				"Home Adress (auto)");
		List<RemovalPoint> removalPointsFound = removalPointService.getAllByMemberId(member.getIdMember());
		if (!removalPointsFound.contains(rp)) {removalPointsFound.add(rp);}
		
		session.setAttribute("allRemovalPoints", removalPointsFound);
		model.addAttribute("allRemovalPoints", removalPointsFound);
		System.out.println(removalPointsFound);
		
		return "article/formCreateArticle";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Valid Article article, BindingResult bindingResult, 
			Model model, HttpSession session) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("validationErrors", bindingResult.getAllErrors());
			return "article/formCreateArticle";
		}
		
		articleService.determineStatusFromDates(article);
		Member member = (Member) session.getAttribute("loggedMember");
		article.setVendor(member);
		article.setSalePrice(article.getStartingPrice());
		
		if (article.getRemovalPoint().getIdRemovalPoint() == 0) {
			this.removalPointService.create(article.getRemovalPoint());
		} // pray for an inplace modification
		System.out.println("\n Sending article to db " + article);
		articleService.create(article);
		
		return "redirect:/";
	}
	
	@GetMapping("/loadArticles")
	public String loadArticles(HttpSession session, Model model) {
		System.out.println("\n article controller get load articles");
		
		Member member = (Member) session.getAttribute("loggedMember");
		if (member != null) {
			model.addAttribute("idMember", member.getIdMember());
			System.out.println("model member id " + member.getIdMember());
		}
		
		List<Category> categoriesFound = categoryService.getAll();
		// model for the html, session for the 1-database-call editor in the init binder
		session.setAttribute("allCategories", categoriesFound); // session so make sure to overwrite it every form
		model.addAttribute("allCategories", categoriesFound);
		System.out.println(categoriesFound);
		
		List<Article> articlesFound = articleService.getAll();
		model.addAttribute("articles", articlesFound);
		System.out.println(articlesFound);
		
		return "encheres";
	}
	
	@PostMapping("/searchArticles")
	public String searchArticles(@RequestParam Map<String,String> allRequestParams, Model model) {
		System.out.println("\n article controller post map search articles");
		System.out.println(allRequestParams);
		Map<String, String> filterMapLike = new HashMap<String, String>();
		Map<String, String> filterMapEquals = new HashMap<String, String>();
		
		for (Entry<String, String> entry : allRequestParams.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			String[] splitKey = key.split("__-__");
			if (splitKey[0].equals("like") && !val.equals("IGNORE") && !val.isBlank() && !val.isEmpty()) {
				filterMapLike.put(splitKey[1], val);
			}
			if (splitKey[0].equals("equals") && !val.equals("IGNORE") && !val.isBlank() && !val.isEmpty()) {
				filterMapEquals.put(splitKey[1], val);
			}
		}
		
		List<Article> articlesFound = articleService.getAll(filterMapLike, filterMapEquals);
		
		model.addAttribute("articles", articlesFound);
		System.out.println(articlesFound);
		
		return "encheres";
	}
	
	
	
	
	// jane_smith
	// password
	
	
	
	
}
