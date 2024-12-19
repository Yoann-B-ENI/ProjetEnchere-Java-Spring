package fr.eni.projetEnchere.bll.article;

import java.util.List;
import java.util.Map;

import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.Article;

public interface ArticleService extends CRUDService<Article>{

	void determineStatusFromDates(Article elem);

	List<Article> getAll(Map<String, String> filterMapLike, Map<String, String> filterMapEquals, int idLoggedMember);
	
	
}
