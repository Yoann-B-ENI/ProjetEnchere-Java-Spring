package fr.eni.projetEnchere.bll.article;

import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.Article;

public interface ArticleService extends CRUDService<Article>{

	void determineStatusFromDates(Article elem);
	
	
}
