package fr.eni.projetEnchere.bll.member;

import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.dal.member.MemberRepository;
import fr.eni.projetEnchere.exception.UserNameAlreadyExistsException;
import jakarta.servlet.http.HttpSession;

@Service
public class MemberServiceImpl implements MemberService {

	Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	MemberRepository memberRepo;

	public MemberServiceImpl(MemberRepository memberRepo) {
		super();
		this.memberRepo = memberRepo;
	}

	@Override
	public void create(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepo.create(member);
	}

	@Override
	public List<Member> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getById(int id) throws EmptyResultDataAccessException {
		return memberRepo.getById(id).get();

	}

	@Override
	public void update(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepo.update(member);
	}

	@Override
	public void delete(int id) {
		memberRepo.delete(id);

	}

	@Override
	public Member getByUserName(String userName) {
		return memberRepo.getByUserName(userName).get();
	}

	@Override
	public void save(Member member, Member loggedMember) throws UserNameAlreadyExistsException {
		logger.debug("passage dans la méthode save");
		Optional<Member> optMember = Optional.empty();
		try {
			logger.debug(member.toString());
			optMember = memberRepo.getByUserName(member.getUserName());
			// logger.debug(member.toString());
		} catch (EmptyResultDataAccessException e) {
			optMember = Optional.empty();
		} finally {
			if (optMember.isEmpty() || member.equals(optMember.get())) {
				if (loggedMember != null) {
					this.update(member);
				} else {
					this.create(member);
				}
			} else {

				throw new UserNameAlreadyExistsException("Pseudo non disponible");
			}
		}
	}

}
