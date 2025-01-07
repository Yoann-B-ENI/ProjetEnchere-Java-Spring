package fr.eni.projetEnchere.dal.bid;

import java.util.List;

import fr.eni.projetEnchere.bo.Bid;
import fr.eni.projetEnchere.dal.CRUDRepository;

public interface BidRepository extends CRUDRepository<Bid>{

	List<Bid> getBidsByArticleId(int idArticle);

}
