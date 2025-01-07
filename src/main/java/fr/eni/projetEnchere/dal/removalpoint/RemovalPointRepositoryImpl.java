package fr.eni.projetEnchere.dal.removalpoint;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.bo.RemovalPoint;

@Repository
public class RemovalPointRepositoryImpl implements RemovalPointRepository{
	
	Logger logger = LoggerFactory.getLogger(RemovalPointRepositoryImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	public RemovalPointRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	@Override
	// sets the new id in the java object inplace
	public void create(RemovalPoint t) {
		logger.debug("DB: create removal point "+t);
		String sql = "INSERT into RemovalPoints (roadNumber, roadName, zipCode, townName, idMember, pointName)"
				+ " VALUES(:roadNumber, :roadName, :zipCode, :townName, :memberTemp, :pointName)";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("roadNumber", t.getRoadNumber());
		paramSource.addValue("roadName", t.getRoadName());
		paramSource.addValue("zipCode", t.getZipCode());
		paramSource.addValue("townName", t.getTownName());
		paramSource.addValue("memberTemp", t.getMember().getIdMember());
		paramSource.addValue("pointName", t.getPointName());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{"idmember"});
		int newId = keyHolder.getKey().intValue();
		if (newId == 0) {logger.error("DB: Error: newly created RemovalPoint got assigned id 0");}
		
		// in place, hope it reflects back up to the previous function call
		t.setIdRemovalPoint(newId);
		logger.debug("> set the id of the new removal point to "+newId);
	}

	@Override
	public List<RemovalPoint> getAll() {
		logger.debug("DB: get all removalpoints");
		String sql = "SELECT * FROM removalpoints ORDER BY (idMember, idRemovalPoint) ASC";
		List<RemovalPoint> removalPointsFound = jdbcTemplate.query(sql, new RemovalPointRowMapper());
		if (removalPointsFound.isEmpty()) {logger.warn("DB: Warn: removal points found list is empty");}
		return removalPointsFound;
	}

	@Override
	public List<RemovalPoint> getAllByMemberId(int idMember) {
		logger.debug("DB: get all removalpoints of member of id "+idMember);
		String sql = "SELECT * FROM removalpoints WHERE idMember = ? ORDER BY (idMember, idRemovalPoint) ASC";
		List<RemovalPoint> removalPointsFound = jdbcTemplate.query(sql, new RemovalPointRowMapper(), idMember);
		if (removalPointsFound.isEmpty()) {logger.warn("DB: Warn: removal points found list is empty");}
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
