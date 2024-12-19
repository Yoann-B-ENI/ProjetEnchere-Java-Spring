package fr.eni.projetEnchere.bll.member;

import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Member getById(int id) {
		return memberRepo.getById(id).get();

	}

	@Override
	public void update(Member member) {
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
	
		if (loggedMember != null) {
			Optional<Member> optMember = memberRepo.getByUserName(member.getUserName());
			logger.debug(member.toString());
			if (optMember.isEmpty() || member.equals(optMember.get())) {
//				try {
					this.update(member);
//				} catch (PSQLException e) {
//					throw new UserNameAlreadyExistsException("Pseudo non disponible");
//				}
			}else {
				
				throw new UserNameAlreadyExistsException("Pseudo non disponible");		
			}	
		} else {
			this.create(member);
		}
	}

}
