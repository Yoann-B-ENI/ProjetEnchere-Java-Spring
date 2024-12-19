package fr.eni.projetEnchere.dal.article;

import java.util.List;
import java.util.Map;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.dal.CRUDRepository;

public interface ArticleRepository extends CRUDRepository<Article>{

	//List<Article> getAll(Map<String, String> filterMapLike, Map<String, String> filterMapEquals);

	List<Article> getAll(Map<String, String> filterMapLike, Map<String, String> filterMapEquals, int idLoggedMember);
	
	
}
