package fr.eni.projetEnchere.dal.member;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Member> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Member> getById(int id) {
		String sql ="idMember, userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townname credits, admin from Members where id = ?";
		
		Member member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Member>(), id); 
		
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
	
}
