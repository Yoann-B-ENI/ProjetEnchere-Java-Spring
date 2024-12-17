package fr.eni.projetEnchere.dal.member;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

	private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryImpl.class);
	
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public MemberRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void create(Member member) {
		String sql ="insert into Members (userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townName, admin, credits) "
				+ "values (:userName, :password, :name, :firstName, :email, :phoneNumber, :roadName, :zipCode, :townName, :admin, :credits)";
//		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); , keyHolder, new String[]{"id"}
		int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member));
		if (nbRows != 1) {
			throw new RuntimeException("Erreur, aucune ligne n'a été ajoutée pour l'utilisateur: " + member);
		}
	}
//	 @Override
//	    public void create(Member member) {
//	        String sql = "INSERT INTO Members (userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townName, admin) " +
//	                     "VALUES (:userName, :password, :name, :firstName, :email, :phoneNumber, :roadName, :zipCode, :townName, :admin)";
//	        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//	        int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member), keyHolder, new String[]{"id"});
//	        if (nbRows != 1) {
//	            throw new RuntimeException("Erreur, aucune ligne n'a été ajoutée pour l'utilisateur: " + member);
//	        }
//	    }

	@Override
	public List<Member> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Member> getById(int id) {
		String sql ="select idMember, userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townname, credits, admin from Members where id = ?";
		
		Member member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), id); 
		
		return Optional.ofNullable(member);
	}

	@Override
	public void update(Member t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Member> getByUserName(String userName) {
		String sql ="select idMember, userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townname, credits, admin from Members where userName = ?";
		
		Member member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), userName); 
		
		return Optional.ofNullable(member);
	}
	
}
