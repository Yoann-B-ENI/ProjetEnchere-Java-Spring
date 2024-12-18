package fr.eni.projetEnchere.dal.article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.standard.expression.Each;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.ArticleStatus;
import fr.eni.projetEnchere.bo.Category;
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.bo.RemovalPoint;

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
	// sets the new id in the java object
	public void create(Article t) {
		String sql = "INSERT into Articles(name, description, auctionStartDate, auctionEndDate, "
				+ "startingPrice, salePrice, status, idVendor, idCategory, idRemovalPoint)";
		sql += "values(:name, :description, :auctionStartDate, :auctionEndDate, "
				+ ":startingPrice, :salePrice, :statusTemp, :vendorTemp, :categoryTemp, :removalPointTemp)";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("name", t.getName());
		paramSource.addValue("description", t.getDescription());
		paramSource.addValue("auctionStartDate", t.getAuctionStartDate());
		paramSource.addValue("auctionEndDate", t.getAuctionEndDate());
		paramSource.addValue("startingPrice", t.getStartingPrice());
		paramSource.addValue("salePrice", t.getSalePrice());
		paramSource.addValue("statusTemp", t.getStatus().toString());
		paramSource.addValue("vendorTemp", t.getVendor().getIdMember());
		paramSource.addValue("categoryTemp", t.getCategory().getIdCategory());
		paramSource.addValue("removalPointTemp", t.getRemovalPoint().getIdRemovalPoint());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{"idarticle"});
		int newId = keyHolder.getKey().intValue();
		
		t.setIdArticle(newId);
	}
	
	
	
	private String processFilters(Map<String, String> filterMapLike, Map<String, String> filterMapEquals) {
		
		String filterString = " ";
		
		if (filterMapLike != null) {
			for (Entry<String, String> entry : filterMapLike.entrySet()) {
				filterString += "AND " + entry.getKey() + " LIKE %" + entry.getValue() + "% ";
			}
		}
		if (filterMapEquals != null) {
			for (Entry<String, String> entry : filterMapEquals.entrySet()) {
				filterString += "AND " + entry.getKey() + " = '" + entry.getValue() + "'";
			}
		}
		
		filterString += " ";//to be safe
		return filterString;
	}


	@Override
	public List<Article> getAll() {// call other one with empty filters
		return this.getAll(null, null);
	}
	
	@Override
	public List<Article> getAll(Map<String, String> filterMapLike, Map<String, String> filterMapEquals) {
		System.out.println("\n > DATABASE : get all articles");
		String sql = "select articles.idArticle, \r\n"
				+ "	articles.name, \r\n"
				+ "	articles.auctionStartDate, \r\n"
				+ "	articles.auctionEndDate, \r\n"
				+ " articles.status, \r\n"
				+ "	articles.salePrice, \r\n"
				+ "	articles.idVendor, \r\n"
				+ "	members.userName, \r\n"
				+ "	articles.idCategory \r\n"
				+ "from articles \r\n"
				+ "JOIN members on articles.idvendor = members.idmember \r\n"
				+ "WHERE 1=1 \r\n"
				+ this.processFilters(filterMapLike, filterMapEquals)
				+ "ORDER BY (articles.auctionEndDate, articles.salePrice) ASC \r\n";
		System.out.println("\n\n > DATABASE ARTICLE FILTER QUERY \n"+sql+"\n\n");
		List<Article> articlesFound = jdbcTemplate.query(sql, new ArticleSmallRowMapper());
		
		return articlesFound;
	}

	@Override
	public Optional<Article> getById(int id) {
		System.out.println("\n > DATABASE : get article of id "+id);
		String sql = "select articles.idArticle, \r\n"
				+ "	articles.name AS article_name, \r\n"
				+ "	articles.auctionStartDate, \r\n"
				+ "	articles.auctionEndDate, \r\n"
				+ " articles.status, \r\n"
				+ "	articles.salePrice, \r\n"
				+ "	articles.startingPrice, \r\n"
				+ "	articles.idCategory, \r\n"
				+ "	categories.name AS cat_name, \r\n"
				+ "	articles.idVendor, \r\n"
				+ "	members.userName, \r\n"
				+ "	articles.idRemovalPoint, \r\n"
				+ "	removalpoints.roadNumber, \r\n"
				+ "	removalpoints.roadName, \r\n"
				+ "	removalpoints.zipCode, \r\n"
				+ "	removalpoints.townName \r\n"
				+ "from articles \r\n"
				+ "JOIN members on articles.idvendor = members.idmember \r\n"
				+ "JOIN categories on articles.idcategory = categories.idcategory \r\n"
				+ "JOIN removalpoints on articles.idremovalpoint = removalpoints.idremovalpoint \r\n"
				+ "WHERE idArticle = ?";
		
		Article art = jdbcTemplate.queryForObject(sql, new ArticleBigRowMapper(), id);
		Optional<Article> return_art = Optional.ofNullable(art);
		return return_art;
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
	
	
	
	
	
	
	
	private static class ArticleSmallRowMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member emptyMember = new Member();
			emptyMember.setIdMember(rs.getInt("idVendor"));
			emptyMember.setUserName(rs.getString("userName"));
			
			Category cat = new Category();
			cat.setIdCategory(rs.getInt("idCategory"));
			
			Article art = new Article();
			art.setVendor(emptyMember);
			art.setCategory(cat);
			
			art.setIdArticle(rs.getInt("idArticle"));
			art.setName(rs.getString("name"));
			art.setAuctionStartDate(rs.getObject("auctionStartDate", LocalDateTime.class));
			art.setAuctionEndDate(rs.getObject("auctionEndDate", LocalDateTime.class));
			art.setStatus(ArticleStatus.valueOf(rs.getString("status")));
			art.setSalePrice(rs.getInt("salePrice"));
			
			return art;
		}
	}
	
	private static class ArticleBigRowMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member emptyMember = new Member();
			emptyMember.setIdMember(rs.getInt("idVendor"));
			emptyMember.setUserName(rs.getString("userName"));
			
			Category cat = new Category();
			cat.setIdCategory(rs.getInt("idCategory"));
			cat.setName(rs.getString("cat_name"));
			
			RemovalPoint rp = new RemovalPoint();
			rp.setIdRemovalPoint(rs.getInt("idRemovalPoint"));
			rp.setRoadNumber(rs.getInt("roadNumber"));
			rp.setRoadName(rs.getString("roadName"));
			rp.setZipCode(rs.getString("zipCode"));
			rp.setTownName(rs.getString("townName"));
			
			Article art = new Article();
			art.setVendor(emptyMember);
			art.setCategory(cat);
			art.setRemovalPoint(rp);
			
			art.setIdArticle(rs.getInt("idArticle"));
			art.setName(rs.getString("article_name"));
			art.setAuctionStartDate(rs.getObject("auctionStartDate", LocalDateTime.class));
			art.setAuctionEndDate(rs.getObject("auctionEndDate", LocalDateTime.class));
			art.setStatus(rs.getObject("status", ArticleStatus.class));
			art.setSalePrice(rs.getInt("salePrice"));
			art.setStartingPrice(rs.getInt("startingPrice"));
			
			return art;
		}
	}



	
	
	
	
	
	
	
	
	
}
