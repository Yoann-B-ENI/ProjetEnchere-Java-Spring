package fr.eni.projetEnchere.bll.bid;

import java.util.List;

import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.Bid;

public interface BidService extends CRUDService<Bid>{

	List<Bid> getBidsByArticleId(int id);

}
