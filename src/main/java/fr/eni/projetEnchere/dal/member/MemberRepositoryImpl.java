package fr.eni.projetEnchere.dal.member;

import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.exception.UserNameAlreadyExistsException;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

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
		logger.debug("DB: create member " + member);
		String sql = "insert into Members (userName, password, name, firstName, email, phoneNumber, roadNumber, roadName, zipCode, townName, admin, credits) "
				+ "values (:userName, :password, :name, :firstName, :email, :phoneNumber, :roadNumber, :roadName, :zipCode, :townName, :admin, :credits)";
		int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member));
		if (nbRows != 1) {
			logger.error("DB: Error: no new line added in DB while creating member");
			throw new RuntimeException("Erreur, aucune ligne n'a été ajoutée pour l'utilisateur: " + member);
		}
	}

	@Override
	public List<Member> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Member> getById(int id) throws EmptyResultDataAccessException {
	logger.debug("DB: get member by id "+id);
		String sql = "select idMember, userName, password, name, firstName, email, phoneNumber, roadNumber, roadName, zipCode, townname, credits, admin "
				+ "from Members where idMember = ?";
		Member member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), id);
		Optional<Member> opt_member = Optional.ofNullable(member);
		if (opt_member.isEmpty()) {logger.warn("DB: Warn: member not found");}
		return opt_member;
	}
	

	@Override
	public void update(Member member) {
		logger.debug("DB: update member " + member);
		String sql = "update Members set " + "userName = :userName, password = :password, "
				+ "name = :name, firstName = :firstName, email = :email, "
				+ "phoneNumber = :phoneNumber, roadNumber = :roadNumber,"
				+ " roadName = :roadName, zipCode = zipCode, townName = :townName, "
				+ "credits = :credits, admin = :admin where idMember = :idMember";
//		try {
		namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member));
//		} catch (Exception e) {
//			throw new PSQLException("pseudo non disponible", null);
//		}

	}

	@Override
	public void delete(int id) {
		logger.debug("DB: delete member of id " + id);
		String sql = "delete from Members where idMember = ?";
		int nbRows = jdbcTemplate.update(sql, id);

	}

	@Override
	public Optional<Member> getByUserName(String userName) throws EmptyResultDataAccessException {
		logger.debug("DB: get member by username " + userName);
		String sql = "select idMember, userName, password, name, firstName, email, phoneNumber, roadNumber, roadName, zipCode, townname, credits, admin "
				+ "from Members where userName = ?";
		Member member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), userName);
		Optional<Member> opt_member = Optional.ofNullable(member);
		if (opt_member.isEmpty()) {
			logger.warn("DB: Warn: member not found");
		}
		return opt_member;
	}

}
