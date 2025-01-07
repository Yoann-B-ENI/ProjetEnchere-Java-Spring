package fr.eni.projetEnchere.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import fr.eni.projetEnchere.bll.bid.BidService;
import fr.eni.projetEnchere.bll.category.CategoryService;
import fr.eni.projetEnchere.bll.removalpoint.RemovalPointService;
import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Bid;
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
	private BidService bidService;


	@Autowired
	public ArticleController(CategoryService categoryService, RemovalPointService removalPointService,
			ArticleService articleService, BidService bidService) {
		super();
		this.categoryService = categoryService;
		this.removalPointService = removalPointService;
		this.articleService = articleService;
		this.bidService = bidService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpSession session) {
		logger.debug("init binder called, binding categories then removal points");
		
		// Use the cached list from session, since we can't pass Model as an argument to the binder
		List<Category> allCategories = (List<Category>) session.getAttribute("allCategories");
        binder.registerCustomEditor(Category.class, new CustomCategoryEditor(allCategories));
        
        List<RemovalPoint> allRemovalPoints = (List<RemovalPoint>) session.getAttribute("allRemovalPoints");
        binder.registerCustomEditor(RemovalPoint.class, new CustomRemovalPointEditor(allRemovalPoints));
    }
	
	
	private Article makeEmptyArticle() {
		Article art = new Article();
		return art;
	}
	
	private void loadCategoriesAndArticles(Model model, HttpSession session) {
		logger.debug("Ctrl: Article Ctrl: load categories and articles");
		
		List<Category> categoriesFound = categoryService.getAll();
		// model for the html, session for the 1-database-call editor in the init binder
		session.setAttribute("allCategories", categoriesFound); // session so make sure to overwrite it every form
		model.addAttribute("allCategories", categoriesFound);
		if (categoriesFound.isEmpty()) {logger.error("Ctrl: ArticleCtrl: > No categories found");}

		Member member = (Member) session.getAttribute("loggedMember");
		
		RemovalPoint rp = new RemovalPoint(0, member.getRoadNumber(), member.getRoadName(), member.getZipCode(),
				member.getTownName(), member, "Home Adress (auto)");
		List<RemovalPoint> removalPointsFound = removalPointService.getAllByMemberId(member.getIdMember());
		if (!removalPointsFound.contains(rp)) {
			removalPointsFound.add(rp);
			logger.debug("> Adding home adress as a potential removal point in the form");
		}
		session.setAttribute("allRemovalPoints", removalPointsFound);
		model.addAttribute("allRemovalPoints", removalPointsFound);
	}

	@GetMapping("/redirectToCreate")
	public String redirectToCreate(Model model, HttpSession session) {
		//logger.debug("Ctrl: Article Ctrl: Redirecting to create article form");
		Article art = this.makeEmptyArticle();
		model.addAttribute("article", art);
		
		this.loadCategoriesAndArticles(model, session);
		return "article/formCreateArticle";
	}
	
	@PostMapping("/redirectToUpdate")
	public String redirectToUpdate(@RequestParam("idArticle") int idArticle, Model model, HttpSession session) {
		//logger.debug("Ctrl: Article Ctrl: Redirecting to create article form");
		
		Member member = (Member) session.getAttribute("loggedMember");
		if (member.equals(null)) {
			logger.error("Ctrl: ArticleCtrl: somehow not logged in while updating an article, must break");
			return "redirect:/";
		}
		
		List<Article> articlesFound = this.articleService.getAllCreatedByMember(member.getIdMember());
		
		Article art = this.articleService.getById(idArticle);
		if (art == null) {
			logger.error("Ctrl: ArticleCtrl: article "+idArticle+" not found, must break");
			return "redirect:/";
		}
		if (!articlesFound.contains(art)){
			logger.error("Ctrl: ArticleCtrl: article "+idArticle+" not found in editable articles of member "
					+member+", must break");
			return "redirect:/";
		}
		model.addAttribute("article", art);

		this.loadCategoriesAndArticles(model, session);
		
		return "article/formCreateArticle";
	}
	

	@PostMapping("/createOrUpdate")
	public String createOrUpdate(@ModelAttribute @Valid Article article, BindingResult bindingResult, 
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
			// pray for an inplace modification
			this.removalPointService.create(article.getRemovalPoint());
			logger.debug("> Auto gen RemovalPoint detected, sending it to DB");
		}
		if (article.getIdArticle() == 0) {
			articleService.create(article);
		}
		else {
			articleService.update(article);
		}

		return "redirect:/";
	}

	@GetMapping("/loadArticles")
	public String loadArticles(HttpSession session, Model model) {
		//logger.debug("Ctrl: Article Ctrl: get load articles");

		Member member = (Member) session.getAttribute("loggedMember");
		if (member != null) {
			model.addAttribute("idMember", member.getIdMember());
			//logger.debug("> model member id " + member.getIdMember());
		}

		List<Category> categoriesFound = categoryService.getAll();
		model.addAttribute("allCategories", categoriesFound);
		
		boolean showAll = true;
		List<Article> articlesFound = new ArrayList<Article>();
		if (showAll) {
			articlesFound = articleService.getAll();
		}
		else {
			Map<String, String> filterMap = new HashMap<String, String>();
			filterMap.put("status", "AuctionStarted");
			articlesFound = articleService.getAll(null, filterMap, 0);
		}
		model.addAttribute("articles", articlesFound);
		
		return "encheres";
	}
	
	
	@PostMapping("/searchArticles")
	public String searchArticles(@RequestParam Map<String,String> allRequestParams, 
			HttpSession session, Model model) {
		logger.debug("Ctrl: Article Ctrl: post map search articles");
		logger.debug("> HTML form params: "+allRequestParams);
		
		FilterSystem filters = new FilterSystem(allRequestParams.entrySet());
		
		Member member = (Member) session.getAttribute("loggedMember");
		int idLoggedMember = 0; // if not logged in
		if (member != null) {
			idLoggedMember = member.getIdMember();
			model.addAttribute("idMember", member.getIdMember());
		}
		logger.debug("> searching with logged member id (0=not logged in): "+idLoggedMember);
		
		List<Article> articlesFound = articleService.getAll(
				filters.getFilterMapLike(), 
				filters.getFilterMapEquals(), 
				idLoggedMember);
		model.addAttribute("articles", articlesFound);
		
		List<Category> categoriesFound = categoryService.getAll();
		model.addAttribute("allCategories", categoriesFound);
		
		return "encheres";
	}
	
	
	@GetMapping("/{id}")
	public String showArticle(@PathVariable("id") int id, Model model, HttpSession session) {
		
		Article art = articleService.getById(id);
		model.addAttribute("article", art);
		
		Member loggedMember = (Member) session.getAttribute("loggedMember");
		model.addAttribute("loggedMember", loggedMember);
		
		return "article/articleDetails";
	}
	
	
	@PostMapping("/bid")
	public String processNewBid(@RequestParam("newPrice") int newPrice, 
								@RequestParam("idArticle") int idArticle, 
								HttpSession session, Model model) {
		// 
		Article article = articleService.getById(idArticle);
		logger.debug("Ctrl: Article Ctrl: received new bid of "+newPrice+" on article with id "+idArticle);
		if (article == null) {
			logger.error("Ctrl: ArticleCtrl: > Article not found on processing bid, must break");
			return "redirect:/";
		}
		
		Member loggedMember = (Member) session.getAttribute("loggedMember");
		if (loggedMember == null) {
			logger.error("Ctrl: ArticleCtrl: > Logged member not found on processing bid, must break");
			return "redirect:/article/"+article.getIdArticle();
		}
		
		Bid bid = new Bid(loggedMember.getIdMember(), article.getIdArticle(), LocalDateTime.now(), newPrice);
		
		if(loggedMember.equals(article.getVendor())) {
			logger.error("Ctrl: ArticleCtrl: > Logged Member is already the vendor, must break");
			return "redirect:/article/"+article.getIdArticle();
		}
		if(loggedMember.getCredits() - newPrice >= 0) {
			logger.debug("> setup and checks passed, going forward with bid processing");
			loggedMember.addCredits(-newPrice);
			if (article.getBuyer() != null) {
				article.getBuyer().addCredits(article.getSalePrice());
			}
			article.setBuyer(loggedMember);
			article.setSalePrice(newPrice);
			articleService.update(article);
			bidService.create(bid);
		}
		else {logger.error("Ctrl: ArticleCtrl: > insufficient member credits");}
		
		return "redirect:/article/"+article.getIdArticle();
	}

	
	@PostMapping("/deleteArticle")
	public String deleteArticle(@RequestParam("idArticle") int idArticle, HttpSession session) {
		
		Member loggedMember = (Member) session.getAttribute("loggedMember");
		if (loggedMember == null) {
			logger.error("Ctrl: ArticleCtrl: > Logged member not found on processing bid, must break");
			return "redirect:/";
		}
		
		Article art = articleService.getById(idArticle);
		if (art == null) {
			logger.error("Ctrl: ArticleCtrl: Error: article asked to be deleted not found");
			return "redirect:/";
		}
		if (!art.getVendor().equals(loggedMember)) {
			logger.error("Ctrl: ArticleCtrl: Error: article asked to be deleted by someone other than article vendor");
		}
		
		logger.debug("Ctrl: ArticleCtrl: sending command to delete article of id "+idArticle);
		articleService.delete(idArticle);
		
		return "redirect:/";
	}
	
	
	
	// jane_smith
	// password
	
	//TODO End of dev : 
	// hide the "toutes encheres" debug option?
	// decide on whether to have the base page show all auctions or just ongoing ones?
	// filter system doc
	
	
	// if vendor and created -> can update
	// if vendor and else -> nothing
	
	// if not and created -> can't see page
	// if not and started -> can bid
	// if buyer and ended -> can see frozen
	// else -> can't see
	
	
	
	
	
	
}
	
	
