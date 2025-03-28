package fr.eni.projetEnchere.dal.article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.ArticleStatus;
import fr.eni.projetEnchere.bo.Category;
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.bo.RemovalPoint;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
	
	private final static int nbArticlesPerPage = 6;
	
	Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Autowired
	public ArticleRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	
	
	private MapSqlParameterSource getArticleParameterSource(Article t) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("name", t.getName());
		paramSource.addValue("idArticle", t.getIdArticle());
		paramSource.addValue("description", t.getDescription());
		paramSource.addValue("auctionStartDate", t.getAuctionStartDate());
		paramSource.addValue("auctionEndDate", t.getAuctionEndDate());
		paramSource.addValue("startingPrice", t.getStartingPrice());
		paramSource.addValue("salePrice", t.getSalePrice());
		paramSource.addValue("statusTemp", t.getStatus().toString());
		paramSource.addValue("vendorTemp", t.getVendor().getIdMember());
		if (t.getBuyer() != null) {
			paramSource.addValue("buyerTemp", t.getBuyer().getIdMember());
		}
		else {
			paramSource.addValue("buyerTemp", null);
		}
		paramSource.addValue("categoryTemp", t.getCategory().getIdCategory());
		paramSource.addValue("removalPointTemp", t.getRemovalPoint().getIdRemovalPoint());
		paramSource.addValue("imgFileName", t.getImgFileName());
		
		return paramSource;
	}
	
	@Override
	// sets the new id in the java object inplace
	public void create(Article t) {
		String sql = "INSERT into Articles(name, description, auctionStartDate, auctionEndDate, "
				+ "startingPrice, salePrice, status, idVendor, idBuyer, idCategory, idRemovalPoint, "
				+ "imgFileName)";
		sql += "values(:name, :description, :auctionStartDate, :auctionEndDate, "
				+ ":startingPrice, :salePrice, :statusTemp, :vendorTemp, :buyerTemp, :categoryTemp, :removalPointTemp, "
				+ ":imgFileName)";
		
		MapSqlParameterSource paramSource = this.getArticleParameterSource(t);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		logger.debug("DB: creating new Article "+t);
		namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{"idarticle"});
		int newId = keyHolder.getKey().intValue();
		if (newId == 0) {logger.error("DB: Error: newly created article got assigned id 0");}
		
		t.setIdArticle(newId);
		logger.debug("> new Article got db id "+newId);
	}
	
	
	
	private String processFilters(Map<String, String> filterMapLike, Map<String, String> filterMapEquals) {
		String filterString = " ";
		if (filterMapLike != null) {
			for (Entry<String, String> entry : filterMapLike.entrySet()) {
				filterString += "AND LOWER(" + entry.getKey() + ") LIKE '%" + entry.getValue().toLowerCase() + "%' ";
			}
		}
		if (filterMapEquals != null) {
			for (Entry<String, String> entry : filterMapEquals.entrySet()) {
				filterString += "AND " + entry.getKey() + " = '" + entry.getValue() + "' ";
			}
		}
		filterString += " ";//to be safe
		return filterString;
	}


	@Override
	public List<Article> getAll() {// call other one with empty filters
		return this.getAll(null, null, 1, 0);
	}
	
	@Override
	public List<Article> getAll(Map<String, String> filterMapLike, Map<String, String> filterMapEquals, 
			int idLoggedMember, int pageNb) {
		String sql = "SELECT * \r\n"
				+ "FROM \r\n"
				+ "(\r\n"
				+ "	SELECT \r\n"
				+ "		art.idArticle, \r\n"
				+ "		art.name, \r\n"
				+ "		art.auctionStartDate, \r\n"
				+ "		art.auctionEndDate, \r\n"
				+ "		art.status, \r\n"
				+ "		art.salePrice, \r\n"
				+ "		art.idVendor, m1.username as username_vendor, \r\n"
				+ "		art.idBuyer, m2.username as username_buyer, \r\n"
				+ "		art.idCategory, \r\n"
				+ "		art.imgFileName, \r\n"
				+ "		(case when exists (select 1 \r\n"
				+ "							from bids \r\n"
				+ "							where bids.idArticle = art.idArticle \r\n"
				+ "							and bids.idMember = ?) \r\n"
				+ "			then 1 else 0 end) as is_found_bid \r\n"
				+ "	FROM articles art \r\n"
				+ "	JOIN members m1 on art.idvendor = m1.idmember \r\n"
				+ "	LEFT JOIN members m2 on art.idbuyer = m2.idmember \r\n"
				+ ") as innerTable \r\n"
				+ "WHERE 1=1 \r\n"
				+ this.processFilters(filterMapLike, filterMapEquals)
				+ "ORDER BY (innerTable.auctionEndDate, innerTable.salePrice) ASC \r\n";
				//+ "LIMIT "+nbArticlesPerPage+" OFFSET "+nbArticlesPerPage*pageNb;
		logger.debug("DB: Article filter query \n"+sql+"\n");
		List<Article> articlesFound = jdbcTemplate.query(sql, new ArticleSmallRowMapper(), idLoggedMember);
		
		if (articlesFound.isEmpty()) {logger.warn("DB: Warn: articles found list is empty");}
		String articlesStr = articlesFound.stream()
                .map(art -> ""+art.getIdArticle()+" "+art.getName())
                .collect(Collectors.joining(", \n"));
		logger.debug("> Articles found: \n"+articlesStr);
		return articlesFound;
	}

	@Override
	public Optional<Article> getById(int id) {
		logger.debug("DB: get Article of id "+id);
		String sql = "SELECT \r\n"
				+ "	art.idArticle, \r\n"
				+ "	art.name as article_name, \r\n"
				+ "	art.description, \r\n"
				+ "	art.auctionStartDate, \r\n"
				+ "	art.auctionEndDate, \r\n"
				+ "	art.status, \r\n"
				+ "	art.startingPrice, \r\n"
				+ "	art.salePrice, \r\n"
				+ "	art.idVendor, m1.username as username_vendor, \r\n"
				+ "	art.idBuyer, m2.username as username_buyer, \r\n"
				+ "	art.idCategory, \r\n"
				+ "	cat.name as cat_name, \r\n"
				+ "	art.idRemovalPoint, \r\n"
				+ " art.imgFileName, \r\n"
				+ "	rp.roadNumber, \r\n"
				+ "	rp.roadName, \r\n"
				+ "	rp.zipCode, \r\n"
				+ "	rp.townName \r\n"
				+ "FROM articles art\r\n"
				+ "JOIN members m1 on art.idvendor = m1.idmember\r\n"
				+ "LEFT JOIN members m2 on art.idbuyer = m2.idmember\r\n"
				+ "JOIN categories cat on art.idcategory = cat.idcategory\r\n"
				+ "JOIN removalpoints rp on art.idremovalpoint = rp.idremovalpoint\r\n"
				+ "WHERE art.idArticle = ?";
		
		Article art = jdbcTemplate.queryForObject(sql, new ArticleBigRowMapper(), id);
		Optional<Article> return_art = Optional.ofNullable(art);
		if (return_art.isEmpty()) {logger.warn("DB: Warn: article not found");}
		return return_art;
	}

	@Override
	public void update(Article t) {
		logger.debug("DB: update article "+t);
		String sql = "UPDATE Articles SET name = :name, description = :description, "
				+ "auctionStartDate = :auctionStartDate, auctionEndDate = :auctionEndDate, "
				+ "startingPrice = :startingPrice, salePrice = :salePrice, "
				+ "status = :statusTemp, "
				+ "idVendor = :vendorTemp, idBuyer = :buyerTemp, idCategory = :categoryTemp, "
				+ "idRemovalPoint = :removalPointTemp, imgFileName = :imgFileName "
				+ " WHERE idArticle = :idArticle";
		
		MapSqlParameterSource paramSource = this.getArticleParameterSource(t);
		namedParameterJdbcTemplate.update(sql, paramSource);
	}

	@Override
	public void delete(int id) {
		logger.debug("DB: deleting article with id "+id);
		String sql = "DELETE from ARTICLES where idArticle = ?";
		jdbcTemplate.update(sql, id);
	}
	
	
	
	
	private static class ArticleSmallRowMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member emptyVendor = new Member();
			emptyVendor.setIdMember(rs.getInt("idVendor"));
			emptyVendor.setUserName(rs.getString("username_vendor"));
			
			Member emptyBuyer = null;
			if (rs.getInt("idBuyer") > 0) {
				emptyBuyer = new Member();
				emptyBuyer.setIdMember(rs.getInt("idBuyer"));
				emptyBuyer.setUserName(rs.getString("username_buyer"));
			}
			
			Category cat = new Category();
			cat.setIdCategory(rs.getInt("idCategory"));
			
			Article art = new Article();
			art.setVendor(emptyVendor);
			art.setBuyer(emptyBuyer);
			art.setCategory(cat);
			
			art.setIdArticle(rs.getInt("idArticle"));
			art.setName(rs.getString("name"));
			art.setAuctionStartDate(rs.getObject("auctionStartDate", LocalDateTime.class));
			art.setAuctionEndDate(rs.getObject("auctionEndDate", LocalDateTime.class));
			art.setStatus(ArticleStatus.valueOf(rs.getString("status")));
			art.setSalePrice(rs.getInt("salePrice"));
			
			art.setImgFileName(rs.getString("imgFileName"));
			
			return art;
		}
	}
	
	
	private static class ArticleBigRowMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member emptyVendor = new Member();
			emptyVendor.setIdMember(rs.getInt("idVendor"));
			emptyVendor.setUserName(rs.getString("username_vendor"));
			
			Member emptyBuyer = null;
			if (rs.getInt("idBuyer") > 0) {
				emptyBuyer = new Member();
				emptyBuyer.setIdMember(rs.getInt("idBuyer"));
				emptyBuyer.setUserName(rs.getString("username_buyer"));
			}
			
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
			art.setVendor(emptyVendor);
			art.setBuyer(emptyBuyer);
			art.setCategory(cat);
			art.setRemovalPoint(rp);
			
			art.setIdArticle(rs.getInt("idArticle"));
			art.setName(rs.getString("article_name"));
			art.setDescription(rs.getString("description"));
			art.setAuctionStartDate(rs.getObject("auctionStartDate", LocalDateTime.class));
			art.setAuctionEndDate(rs.getObject("auctionEndDate", LocalDateTime.class));
			art.setStatus(ArticleStatus.valueOf(rs.getString("status")));
			art.setStartingPrice(rs.getInt("startingPrice"));
			art.setSalePrice(rs.getInt("salePrice"));
			
			art.setImgFileName(rs.getString("imgFileName"));
			
			return art;
		}
	}



	
	
	
	
	
	
	
	
	
}
