package fr.eni.projetEnchere.bll.bid;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.Bid;
import fr.eni.projetEnchere.dal.bid.BidRepository;

@Service
public class BidServiceImpl implements BidService{

	private BidRepository bidRepo;

	public BidServiceImpl(BidRepository bidRepo) {
		super();
		this.bidRepo = bidRepo;
	}

	@Override
	public void create(Bid t) {
		this.bidRepo.create(t);
	}

	@Override
	public List<Bid> getAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public Bid getById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public void update(Bid t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public List<Bid> getBidsByArticleId(int idArticle) {
		return this.bidRepo.getBidsByArticleId(idArticle);
	}

}
