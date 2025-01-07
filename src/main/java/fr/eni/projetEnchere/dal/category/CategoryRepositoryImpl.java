package fr.eni.projetEnchere.dal.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Category;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{
	
	Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Autowired
	public CategoryRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	@Override
	public void create(Category t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public List<Category> getAll() {
		logger.debug("DB: get all categories");
		
		String sql = "SELECT * FROM categories ORDER BY idCategory ASC";
		List<Category> categoriesFound = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
		
		if (categoriesFound.isEmpty()) {logger.warn("DB: Warn: categories found list is empty");}
		String categoriesStr = categoriesFound.stream()
                .map(c -> ""+c.getIdCategory()+" "+c.getName())
                .collect(Collectors.joining(", \n"));
		logger.debug("> categories found: \n"+categoriesStr);
		return categoriesFound;
	}

	@Override
	public Optional<Category> getById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public void update(Category t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

}
