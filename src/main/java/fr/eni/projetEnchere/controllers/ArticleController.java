package fr.eni.projetEnchere.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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


	Logger logger = LoggerFactory.getLogger(ArticleController.class);

	private ArticleService articleService;
	private CategoryService categoryService;
	private RemovalPointService removalPointService;


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
		//logger.debug("init binder called, binding category");
		// Use the cached list from session, since we can't pass Model as an argument
		List<Category> allCategories = (List<Category>) session.getAttribute("allCategories");

        binder.registerCustomEditor(Category.class, new CustomCategoryEditor(allCategories));
        
        //logger.debug("> > now binding removalPoint");
        List<RemovalPoint> allRemovalPoints = (List<RemovalPoint>) session.getAttribute("allRemovalPoints");
        binder.registerCustomEditor(RemovalPoint.class, new CustomRemovalPointEditor(allRemovalPoints));
    }

	@GetMapping("/redirectToCreate")
	public String redirectToCreate(Model model, HttpSession session) {
		//logger.debug("\n Redirecting to create article form");

		List<Category> categoriesFound = categoryService.getAll();
		// model for the html, session for the 1-database-call editor in the init binder
		session.setAttribute("allCategories", categoriesFound); // session so make sure to overwrite it every form
		model.addAttribute("allCategories", categoriesFound);
		//logger.debug(categoriesFound);

		Member member = (Member) session.getAttribute("loggedMember");
		RemovalPoint rp = new RemovalPoint(0, member.getRoadNumber(), member.getRoadName(), member.getZipCode(),
				member.getTownName(), member, "Home Adress (auto)");
		List<RemovalPoint> removalPointsFound = removalPointService.getAllByMemberId(member.getIdMember());
		if (!removalPointsFound.contains(rp)) {
			removalPointsFound.add(rp);
		}

		session.setAttribute("allRemovalPoints", removalPointsFound);
		model.addAttribute("allRemovalPoints", removalPointsFound);
		//logger.debug(removalPointsFound);

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
		article.setBuyer(null);
		article.setSalePrice(article.getStartingPrice());

		if (article.getRemovalPoint().getIdRemovalPoint() == 0) {
			this.removalPointService.create(article.getRemovalPoint());
		} // pray for an inplace modification
		//logger.debug("\n Sending article to db " + article);
		articleService.create(article);

		return "redirect:/";
	}

	@GetMapping("/loadArticles")
	public String loadArticles(HttpSession session, Model model) {
		//logger.debug("\n article controller get load articles");

		Member member = (Member) session.getAttribute("loggedMember");
		if (member != null) {
			model.addAttribute("idMember", member.getIdMember());
			//logger.debug("model member id " + member.getIdMember());
		}

		List<Category> categoriesFound = categoryService.getAll();
		model.addAttribute("allCategories", categoriesFound);
		//categoriesFound.forEach(c -> System.out.println(c +"\n"));
		
		List<Article> articlesFound = articleService.getAll();
		model.addAttribute("articles", articlesFound);
		//articlesFound.forEach(art -> System.out.println(art +"\n"));
		
		return "encheres";
	}
	
	
	@PostMapping("/searchArticles")
	public String searchArticles(@RequestParam Map<String,String> allRequestParams, 
			HttpSession session, Model model) {
		System.out.println("\n article controller post map search articles");
		System.out.println(allRequestParams);
		
		FilterSystem filters = new FilterSystem(allRequestParams.entrySet());
		
		Member member = (Member) session.getAttribute("loggedMember");
		int idLoggedMember = 1;
		if (member != null) {
			idLoggedMember = member.getIdMember();
			model.addAttribute("idMember", member.getIdMember());
			System.out.println("model member id " + member.getIdMember());
		}
		List<Article> articlesFound = articleService.getAll(
				filters.getFilterMapLike(), 
				filters.getFilterMapEquals(), 
				idLoggedMember);
		
		model.addAttribute("articles", articlesFound);
		//articlesFound.forEach(art -> System.out.println(art +"\n"));
		
		List<Category> categoriesFound = categoryService.getAll();
		model.addAttribute("allCategories", categoriesFound);
		
		return "encheres";
	}
	
	
	@GetMapping("/{id}")
	public String showArticle(@PathVariable("id") int id, Model model) {
		
		Article art = articleService.getById(id);
		model.addAttribute("article", art);
		
		return "article/articleDetails";
	}
	
	@PostMapping("/bid")
	public String processNewBid(@RequestParam("newPrice") int newPrice, 
								@RequestParam("articleId") int articleId, 
								HttpSession session, Model model) {
		System.out.println(newPrice);
		System.out.println(articleId);
		
		Article article = articleService.getById(articleId);
		System.out.println("article bid on: "+article);
		
		Member loggedMember = (Member) session.getAttribute("loggedMember");
		System.out.println("logged member: "+loggedMember);
		
		if(loggedMember.equals(article.getVendor())) {
			System.err.println("Logged Member is already the vendor, should break but will continue for dev");
		}
		if(loggedMember.getCredits() - newPrice >= 0) {
			loggedMember.addCredits(-newPrice);
			if (article.getBuyer() != null) {
				article.getBuyer().addCredits(article.getSalePrice());
			}

			article.setBuyer(loggedMember);
			article.setSalePrice(newPrice);
			articleService.update(article);
		}
		else {System.err.println("insufficient credits error");}
		
		return "redirect:/article/"+article.getIdArticle();
	}
	// @RequestParam("articleId") int articleId, 
	
	
	
	// jane_smith
	// password
	
	//TODO End of dev : 
	// re-put the 'toutes encheres en cours' to AuctionStarted and not IGNORE
	// make a logged member not able to bit on their own bids (they just don't see the line?)
	
	
	
	
}
	
	
