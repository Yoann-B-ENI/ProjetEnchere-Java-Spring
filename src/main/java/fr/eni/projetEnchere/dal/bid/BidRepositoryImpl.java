package fr.eni.projetEnchere.dal.bid;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Bid;
import fr.eni.projetEnchere.dal.article.ArticleRepositoryImpl;


@Repository
public class BidRepositoryImpl implements BidRepository{

	Logger logger = LoggerFactory.getLogger(BidRepositoryImpl.class);
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public BidRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}
	
	
	
	@Override
	public void create(Bid t) {
		logger.debug("DB: create bid "+t);
		String sql = "INSERT into BIDS(idMember, idArticle, bidDate, bidPrice) values(?, ?, ?, ?)";
		jdbcTemplate.update(sql, t.getIdMember(), t.getIdArticle(), t.getBidDate(), t.getBidPrice());
	}

	@Override
	public List<Bid> getAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public Optional<Bid> getById(int id) {
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

}
