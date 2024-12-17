package fr.eni.projetEnchere.dal.removalpoint;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.RemovalPoint;

@Repository
public class RemovalPointRepositoryImpl implements RemovalPointRepository{
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	

	public RemovalPointRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	@Override
	public void create(RemovalPoint t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public List<RemovalPoint> getAll() {
		System.out.println("DATABASE : get all removalpoints");
		String sql = "SELECT * FROM removalpoints ORDER BY idRemovalPoint ASC";
		List<RemovalPoint> removalPointsFound = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RemovalPoint.class));
		return removalPointsFound;
	}

	@Override
	public Optional<RemovalPoint> getById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public void update(RemovalPoint t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

}
