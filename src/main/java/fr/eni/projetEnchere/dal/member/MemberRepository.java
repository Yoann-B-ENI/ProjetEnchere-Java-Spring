package fr.eni.projetEnchere.dal.member;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;

import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.dal.CRUDRepository;

public interface MemberRepository extends CRUDRepository<Member>{
	public Optional<Member> getByUserName(String userName) throws EmptyResultDataAccessException;
}
