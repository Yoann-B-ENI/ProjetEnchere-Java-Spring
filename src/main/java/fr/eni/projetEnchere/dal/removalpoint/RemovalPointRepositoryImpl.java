package fr.eni.projetEnchere.dal.removalpoint;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Member;
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
		String sql = "SELECT * FROM removalpoints ORDER BY (idMember, idRemovalPoint) ASC";
		List<RemovalPoint> removalPointsFound = jdbcTemplate.query(sql, new RemovalPointRowMapper());
		return removalPointsFound;
	}

	@Override
	public List<RemovalPoint> getAllByMemberId(int idMember) {
		System.out.println("DATABASE : get all removalpoints of Member "+idMember);
		String sql = "SELECT * FROM removalpoints WHERE idMember = ? ORDER BY (idMember, idRemovalPoint) ASC";
		List<RemovalPoint> removalPointsFound = jdbcTemplate.query(sql, new RemovalPointRowMapper(), idMember);
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
	
	
	private static class RemovalPointRowMapper implements RowMapper<RemovalPoint>{
		@Override
		public RemovalPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member emptyMember = new Member();
			emptyMember.setIdMember(rs.getInt("idMember"));
			
			RemovalPoint rp = new RemovalPoint();
			rp.setMember(emptyMember);
			
			rp.setIdRemovalPoint(rs.getInt("idRemovalPoint"));
			rp.setRoadNumber(rs.getInt("RoadNumber"));
			rp.setRoadName(rs.getString("roadName"));
			rp.setZipCode(rs.getString("zipCode"));
			rp.setTownName(rs.getString("townName"));
			rp.setPointName(rs.getString("pointName"));
			
			return rp;
		}
	}
	
	
	

}
