package fr.eni.projetEnchere.bll.article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.ArticleStatus;
import fr.eni.projetEnchere.dal.article.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	private ArticleRepository articleRepository;
	
	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository) {
		super();
		this.articleRepository = articleRepository;
	}

	@Override
	public void create(Article t) {
		this.articleRepository.create(t);
	}

	@Override
	public List<Article> getAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public Article getById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public void update(Article t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void determineStatusFromDates(Article elem) {
		if (LocalDateTime.now().isBefore(elem.getAuctionStartDate())) {
			elem.setStatus(ArticleStatus.Created);
		}
		else if (elem.getAuctionStartDate().isBefore(LocalDateTime.now())){
			elem.setStatus(ArticleStatus.AuctionStarted);
		}
		else if (elem.getAuctionEndDate().isBefore(LocalDateTime.now())){
			elem.setStatus(ArticleStatus.AuctionEnded);
		}
	}
	
	
	
	
}
