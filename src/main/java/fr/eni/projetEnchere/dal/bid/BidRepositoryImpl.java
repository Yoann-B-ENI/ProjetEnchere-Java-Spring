package fr.eni.projetEnchere.dal.bid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Bid;
import fr.eni.projetEnchere.bo.Member;
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
		jdbcTemplate.update(sql, t.getMember().getIdMember(), t.getIdArticle(), t.getBidDate(), t.getBidPrice());
	}

	@Override
	public List<Bid> getAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}
	
	@Override
	public List<Bid> getBidsByArticleId(int idArticle) {
		logger.debug("DB: Bid: get all bids of article with id "+idArticle);
		String sql = "SELECT \r\n"
				+ "idBid, \r\n"
				+ "bids.idMember, \r\n"
				+ "userName, \r\n"
				+ "idArticle, \r\n"
				+ "bidDate, \r\n"
				+ "bidPrice \r\n"
				+ "FROM Bids\r\n"
				+ "JOIN Members on Bids.idMember = Members.idMember \r\n"
				+ "WHERE idArticle=?\r\n"
				+ "ORDER BY bidDate ASC\r\n"
				+ ";";
		List<Bid> bidsFound = jdbcTemplate.query(sql, new BidRowMapper(), idArticle);
		if (bidsFound.isEmpty()) {logger.warn("Found no bids for article with id "+idArticle);}
		return bidsFound;
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
	
	
	
	private class BidRowMapper implements RowMapper<Bid>{
		@Override
		public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member m = new Member();
			m.setIdMember(rs.getInt("idMember"));
			m.setUserName(rs.getString("userName"));
			
			Bid b = new Bid();
			b.setIdBid(rs.getInt("idBid"));
			b.setMember(m);
			b.setBidDate(rs.getObject("bidDate", LocalDateTime.class));
			b.setBidPrice(rs.getInt("bidPrice"));
			
			return b;
		}
	}
	
	
	
	
	
	
	
	
	
	
}
