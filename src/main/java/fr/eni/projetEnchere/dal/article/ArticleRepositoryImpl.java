package fr.eni.projetEnchere.dal.article;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Article;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	
	public ArticleRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	@Override
	public void create(Article t) {
		String sql = "INSERT into Articles(name, description, auctionStartDate, auctionEndDate, "
				+ "startingPrice, status, idVendor, idCategory, idRemovalPoint)";
		sql += "values(:name, :description, :auctionStartDate, :auctionEndDate, "
				+ ":startingPrice, :statusTemp, :vendorTemp, :categoryTemp, :removalPointTemp)";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("name", t.getName());
		paramSource.addValue("description", t.getDescription());
		paramSource.addValue("auctionStartDate", t.getAuctionStartDate());
		paramSource.addValue("auctionEndDate", t.getAuctionEndDate());
		paramSource.addValue("startingPrice", t.getStartingPrice());
		paramSource.addValue("statusTemp", t.getStatus().toString());
		paramSource.addValue("vendorTemp", t.getVendor().getIdMember()); //TODO make member, get id
		paramSource.addValue("categoryTemp", t.getCategory().getIdCategory());
		paramSource.addValue("removalPointTemp", t.getRemovalPoint().getIdRemovalPoint());
		namedParameterJdbcTemplate.update(sql, paramSource);
	}

	@Override
	public List<Article> getAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public Optional<Article> getById(int id) {
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


}
