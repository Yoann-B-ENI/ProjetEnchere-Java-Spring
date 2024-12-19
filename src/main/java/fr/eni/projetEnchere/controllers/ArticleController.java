package fr.eni.projetEnchere.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.bo.RemovalPoint;
import fr.eni.projetEnchere.controllers.converters.CustomCategoryEditor;
import fr.eni.projetEnchere.controllers.converters.CustomRemovalPointEditor;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class ArticleController {

	Logger logger = LoggerFactory.getLogger(ArticleController.class);

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
	public String create(@ModelAttribute Article article, Model model, HttpSession session) {

		articleService.determineStatusFromDates(article);
		Member member = (Member) session.getAttribute("loggedMember");
		article.setVendor(member);
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
		// model for the html, session for the 1-database-call editor in the init binder
		session.setAttribute("allCategories", categoriesFound); // session so make sure to overwrite it every form
		model.addAttribute("allCategories", categoriesFound);
		//logger.debug(categoriesFound);

		List<Article> articlesFound = articleService.getAll();
		model.addAttribute("articles", articlesFound);
		//logger.debug(articlesFound);

		return "encheres";
	}

	@PostMapping("/searchArticles")
	public String searchArticles(@RequestParam Map<String, String> allRequestParams, Model model) {
		//logger.debug("\n article controller post map search articles");
		//logger.debug(allRequestParams);
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
		//logger.debug(articlesFound);

		return "encheres";
	}

	// jane_smith
	// password

}
