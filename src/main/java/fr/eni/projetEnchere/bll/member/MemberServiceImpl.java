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
	public Member save(Member member, Member loggedMember) throws UserNameAlreadyExistsException {
		logger.debug("passage dans la méthode save");
		Optional<Member> optMember = Optional.empty();
		Member updatedMember = new Member();
		try {
			if (loggedMember != null){
				member.setIdMember(loggedMember.getIdMember());
				//logger.debug("loggedMember: " + loggedMember.toString());
			}
			logger.debug("loggedMember: " + member.toString());
			optMember = memberRepo.getByUserName(member.getUserName());
			logger.debug(" try : " + member.toString());
		} catch (EmptyResultDataAccessException e) {
			logger.debug(" catch : " + optMember.toString());
			optMember = Optional.empty();
			if (loggedMember != null) {
				if (optMember.isEmpty() || member.equals(optMember.get())) {
					this.update(member);
					updatedMember = this.getById(member.getIdMember());
					
				}
			} else if (loggedMember == null && optMember.isEmpty()) {
				this.create(member);
				updatedMember = this.getByUserName(member.getUserName());
				
			} else {
				throw new UserNameAlreadyExistsException("Pseudo non disponible");
			}
			return updatedMember;
		}
		
		if (loggedMember != null) {
			if (optMember.isEmpty() || member.equals(optMember.get())) {
				this.update(member);
				updatedMember = this.getById(member.getIdMember());
				
			} else {
				throw new UserNameAlreadyExistsException("Pseudo non disponible");
			}
		} else if (loggedMember == null && optMember.isEmpty()) {
			this.create(member);
			updatedMember = this.getByUserName(member.getUserName());
			
		} else {
			throw new UserNameAlreadyExistsException("Pseudo non disponible");
		}
		return updatedMember;
	}
}